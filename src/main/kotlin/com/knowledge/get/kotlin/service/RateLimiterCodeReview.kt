package com.knowledge.get.kotlin.service

import com.github.benmanes.caffeine.cache.Caffeine
import java.util.*
import java.util.concurrent.*
import java.util.concurrent.locks.*

fun main() {
    val externalService = ExternalService()
    for (i in 0..505) {
        externalService.doRequest()
    }
}

interface RateLimiter {
    fun allowRequest(): Boolean
    fun increment()
}

class TokenBucketRateLimiter: RateLimiter {
    private val reentrantLock: ReentrantLock = ReentrantLock()
    private val caffeineCache = Caffeine.newBuilder()
    .maximumSize(1000)
        .expireAfterWrite(1, TimeUnit.SECONDS)
        .build<String, Int>()

    override fun allowRequest(): Boolean {
        return caffeineCache.asMap().size <= 500
    }

    override fun increment() {
        try {
            reentrantLock.lock()
            caffeineCache.put(UUID.randomUUID().toString(), 1)
        } finally {
            reentrantLock.unlock()
        }
    }
}

class ExternalService {
    private val limiter = TokenBucketRateLimiter()
    fun doRequest() {
        if (limiter.allowRequest()) {
            limiter.increment()
            println("doRequest")
        } else {
            println("Can't doRequest")
        }
    }
}

class ActualTokenBucketRateLimiter(
    private val capacity: Int = 500,
    private val refillRatePerSecond: Int = 50
) : RateLimiter {

    private var tokens: Double = capacity.toDouble()
    private var lastRefillTimestamp: Long = System.nanoTime()
    private val lock = ReentrantLock()

    override fun allowRequest(): Boolean {
        lock.lock()
        try {
            refill()
            return if (tokens >= 1) {
                tokens -= 1
                true
            } else {
                false
            }
        } finally {
            lock.unlock()
        }
    }

    override fun increment() {
        // Not needed in TokenBucket model
    }

    private fun refill() {
        val now = System.nanoTime()
        val elapsedSeconds = (now - lastRefillTimestamp) / 1_000_000_000.0
        val tokensToAdd = elapsedSeconds * refillRatePerSecond
        tokens = minOf(capacity.toDouble(), tokens + tokensToAdd)
        lastRefillTimestamp = now
    }
}
