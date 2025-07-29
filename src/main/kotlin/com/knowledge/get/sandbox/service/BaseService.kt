package com.knowledge.get.sandbox.service

interface BaseService<T, ID> {
    fun create(entity: T): T
    fun get(id: ID): T?
    fun getAll(): List<T>
    fun delete(id: ID): Boolean
}
