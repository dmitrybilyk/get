package com.knowledge.get.repository

import com.knowledge.get.model.Person
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface PersonRepository : ReactiveMongoRepository<Person, String>
