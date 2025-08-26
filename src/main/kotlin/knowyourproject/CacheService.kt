import com.github.benmanes.caffeine.cache.AsyncLoadingCache
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.CacheLoader
import java.time.Duration
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executor
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.atomic.AtomicInteger
import java.util.logging.Logger

// Custom exception for cache name conflicts
class CacheNameAlreadyExistsException(message: String) : RuntimeException(message)

// Custom loader interface for async loading
fun interface Loader<K, V> {
    fun load(key: K): CompletableFuture<V>
}

// Main CacheService class
class CacheService {
    companion object {
        private val LOGGER = Logger.getLogger(CacheService::class.java.name)
    }

    private val caches = ConcurrentHashMap<String, AsyncLoadingCache<*, *>>()
    private val evictedBySizeCounters = ConcurrentHashMap<String, AtomicInteger>()

    // Create a refreshable async cache (Caffeine 3.1.1)
    fun <K : Any, V : Any> createRefreshable(
        name: String,
        maximumSize: Long,
        expireAfterWrite: Duration? = null,
        refreshAfterWrite: Duration? = null,
        loader: Loader<K, V>,
        executor: Executor = ForkJoinPool.commonPool()
    ): AsyncLoadingCache<K, V> {
        val builder = Caffeine.newBuilder()
            .maximumSize(maximumSize)
        expireAfterWrite?.let { builder.expireAfterWrite(it) }
        refreshAfterWrite?.let { builder.refreshAfterWrite(it) }

        val cache = buildCache(name, builder, loader, executor)
        return cache
    }

    // Build the cache internally
    private fun <K : Any, V : Any> buildCache(
        name: String,
        builder: Caffeine<Any, Any>,
        loader: Loader<K, V>,
        executor: Executor
    ): AsyncLoadingCache<K, V> {
        if (caches.containsKey(name)) {
            throw CacheNameAlreadyExistsException("Cache '$name' already exists")
        }

        val evictedBySizeCounter = AtomicInteger(0)
        evictedBySizeCounters[name] = evictedBySizeCounter

        val cache = builder.buildAsync(object : CacheLoader<K, V> {
            override fun load(key: K): V {
                // Fallback to sync when Caffeine internally calls load()
                return loader.load(key).join()
            }

            override fun asyncLoad(key: K, executor: Executor): CompletableFuture<V> {
                return loader.load(key)
            }
        })

        caches[name] = cache
        return cache
    }

    // Get a value from the cache (async, returns future)
    fun <K : Any, V : Any> get(name: String, key: K): CompletableFuture<V>? {
        @Suppress("UNCHECKED_CAST")
        val cache = caches[name] as? AsyncLoadingCache<K, V>
        return cache?.get(key)
    }

    // Get eviction count for a cache
    fun getEvictedBySizeCount(name: String): Int {
        return evictedBySizeCounters[name]?.get() ?: 0
    }
}

fun main() {
    val cacheService = CacheService()

    // Define a simple loader that simulates async loading with delay
    val loader = Loader<String, String> { key ->
        CompletableFuture.supplyAsync {
            Thread.sleep(500) // Simulate delay
            "Value for $key"
        }
    }

    // Create a cache with max size 2, expire after 10 seconds write, refresh after 5 seconds
    val cache = cacheService.createRefreshable(
        name = "testCache",
        maximumSize = 2,
        expireAfterWrite = Duration.ofSeconds(10),
        refreshAfterWrite = Duration.ofSeconds(5),
        loader = loader
    )

    // Test getting values
    println("Getting key1: ${cacheService.get<String, String>("testCache", "key1")?.join()}")
    println("Getting key2: ${cacheService.get<String, String>("testCache", "key2")?.join()}")
    println("Getting key3 (should evict one): ${cacheService.get<String, String>("testCache", "key3")?.join()}")

    // Check eviction count
    println("Evicted by size count: ${cacheService.getEvictedBySizeCount("testCache")}")

    // Wait for expiration
    println("Waiting for expiration...")
    Thread.sleep(11000)
    println("Getting key1 after expiration: ${cacheService.get<String, String>("testCache", "key1")?.join()}")
}
