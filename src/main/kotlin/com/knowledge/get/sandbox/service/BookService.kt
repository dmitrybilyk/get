package com.knowledge.get.sandbox.service

import com.knowledge.get.sandbox.model.Book
import com.knowledge.get.sandbox.repository.CrudRepository


class BookService(private val repository: CrudRepository<Book, String>) :
    BaseService<Book, String> {

    override fun create(entity: Book): Book = repository.save(entity)

    override fun get(id: String): Book? = repository.findById(id)

    override fun getAll(): List<Book> = repository.findAll()

    override fun delete(id: String): Boolean = repository.delete(id)
}
