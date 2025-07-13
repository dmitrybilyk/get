package com.knowledge.get.dao

import reactor.core.publisher.Flux

abstract class GenericDao<T> (
    private val clazz: Class<T>
) {
    fun getAll(): Flux<T> {
        val start = System.currentTimeMillis()

        return doGetAll()
            .doFinally {
                val duration = System.currentTimeMillis() - start
                println("[${clazz.simpleName}] getAll took ${duration}ms")
            }
    }

    protected abstract fun doGetAll(): Flux<T>
}