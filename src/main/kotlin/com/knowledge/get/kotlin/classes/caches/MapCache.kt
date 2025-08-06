package com.knowledge.get.kotlin.classes.caches

import com.github.benmanes.caffeine.cache.Caffeine
import java.time.Duration
import java.time.Instant
import java.util.concurrent.*


fun main() {
    val repo = UserRepository()
    val service = UserService(repo)

    println(service.getUserById(1)) // 🟡 First call: goes to DB
    println(service.getUserById(1)) // 🟢 Second call: comes from cache
    println(service.getUserById(2)) // 🟡 Goes to DB
    println(service.getUserById(2)) // 🟢 Comes from cache

//    val cache = LRUCache<Int, String>(maxSize = 3)
//
//    cache.put(1, "One")
//    cache.put(2, "Two")
//    cache.put(3, "Three")
//
//    println(cache.keys()) // [1, 2, 3]
//
//    cache.get(1)          // Access key 1 to make it "recently used"
//    cache.put(4, "Four")  // This will evict key 2 (least recently used)
//
//    println(cache.keys()) // [3, 1, 4]
}


class MapCache<K, V> {
    private val map = mutableMapOf<K, V>()

    fun put(key: K, value: V) {
        map[key] = value
    }

    fun get(key: K): V? {
        return map[key]
    }

    fun contains(key: K): Boolean {
        return map.containsKey(key)
    }

    fun remove(key: K) {
        map.remove(key)
    }
}

data class User(val id: Int, val name: String)

class UserRepository {
    fun getUserFromDb(id: Int): User {
        println("Fetching from DB for id=$id")
        Thread.sleep(500) // simulate delay
        return User(id, "User$id")
    }
}

class UserService(
    private val repository: UserRepository
) {
    private val loadingCache = Caffeine.newBuilder()
        .expireAfterWrite(5, TimeUnit.MINUTES)
        .maximumSize(100)
        .build<Int, User> {
            repository.getUserFromDb(it) }

    //    private val cache = LRUCache<Int, User>(100)
    private val cache = TTLCache<Int, User>(Duration.ofMinutes(5))

    fun getUserById(id: Int): User {
        return loadingCache.get(id)
//        return cache.get(id) ?: run {
//            val user = repository.getUserFromDb(id)
//            cache.put(id, user)
//            user
//        }
    }
}

class LRUCache<K, V>(val maxSize: Int) {
    private val cache: LinkedHashMap<K,V> = object: LinkedHashMap<K, V>(16, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean {
            return size > maxSize
        }
    }

    fun put(key: K, value: V) {
        cache[key] =  value
    }

    operator fun get(key: K): V? {
        return cache[key]
    }

    fun contains(key: K): Boolean = key in cache

    fun remove(key: K): V? = cache.remove(key)

    fun clear() = cache.clear()

    fun size(): Int = cache.size

    fun keys(): Set<K> = cache.keys

}

class TTLCache<K, V>(private  val ttl: Duration) {
    private data class CacheEntry<V>(val value: V, val expiresAt: Instant)

    private val cache = ConcurrentHashMap<K, CacheEntry<V>>()

    fun put(key: K, value: V) {
        val expiresAt = Instant.now().plus(ttl)
        cache[key] = CacheEntry(value, expiresAt)
    }

    fun get(key: K): V? {
        val cacheEntry = cache[key]
        if (cacheEntry != null) {
            if (Instant.now().isBefore(cacheEntry.expiresAt)) {
                return cacheEntry.value
            } else {
                cache.remove(key)
            }
        }
        return null
    }

    fun contains(key: K): Boolean = get(key) != null

    fun remove(key: K): V? = cache.remove(key)?.value

    fun clear() = cache.clear()
}
