package com.knowledge.get.sandbox.repository

class InMemoryRepository<T: Any, ID>(
    private val idSelector: (T) -> ID
) : CrudRepository<T, ID> {

    private val store = mutableMapOf<ID, T>()

    override fun save(entity: T): T {
        val id = idSelector(entity)
        store[id] = entity
        return entity
    }

    override fun findById(id: ID): T? = store[id]

    override fun findAll(): List<T> = store.values.toList()

    override fun delete(id: ID): Boolean = store.remove(id) != null
}