package com.knowledge.get.dao.impl

import com.knowledge.get.dao.GenericDao
import com.knowledge.get.dao.api.PersonDao
import com.knowledge.get.model.Person
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux


@Repository
class PersonDaoMongoImpl(
    private val mongoTemplate: ReactiveMongoTemplate
) : GenericDao<Person>(Person::class.java), PersonDao {

    override fun doGetAll(): Flux<Person> {
        // Add some custom behavior here
        println("Executing custom logic for PersonDao")

        val query = Query() // could be built dynamically
        return mongoTemplate.find(query, Person::class.java)
    }
}