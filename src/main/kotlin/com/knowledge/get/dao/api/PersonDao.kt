package com.knowledge.get.dao.api

import com.knowledge.get.model.Person
import reactor.core.publisher.Flux

interface PersonDao {
    fun getAll(): Flux<Person>
}