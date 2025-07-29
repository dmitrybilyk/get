package com.knowledge.get.sandbox.service

import com.knowledge.get.sandbox.model.User
import com.knowledge.get.sandbox.repository.CrudRepository


class UserService(private val repository: CrudRepository<User, String>) :
    BaseService<User, String> {

    override fun create(entity: User): User = repository.save(entity)

    override fun get(id: String): User? = repository.findById(id)

    override fun getAll(): List<User> = repository.findAll()

    override fun delete(id: String): Boolean = repository.delete(id)
}
