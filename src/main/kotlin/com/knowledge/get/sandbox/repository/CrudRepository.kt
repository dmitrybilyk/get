package com.knowledge.get.sandbox.repository

interface CrudRepository<T, ID> {
    fun save(entity: T): T
    fun findById(id: ID): T?
    fun findAll(): List<T>
    fun delete(id: ID): Boolean
}