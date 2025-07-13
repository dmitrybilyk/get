package com.knowledge.get.service

import com.knowledge.get.dao.api.PersonDao
import com.knowledge.get.dao.impl.PersonDaoMongoImpl
import com.knowledge.get.model.Address
import com.knowledge.get.model.Person
import com.knowledge.get.repository.PersonRepository
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class PersonService(private val repo: PersonRepository,
                    private val mongoTemplate: ReactiveMongoTemplate,
                    private val personDao: PersonDao
) {

//    fun getAll(): Flux<Person> = repo.findAll()
    fun getAll(): Flux<Person> = personDao.getAll()

    fun getById(id: String): Mono<Person> = repo.findById(id)

    fun create(person: Person): Mono<Person> = repo.save(person)

    fun delete(id: String): Mono<Void> = repo.deleteById(id)

    fun getPersonWithAddresses(id: String): Mono<PersonWithAddresses> {
        val aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("_id").`is` (id)),
            Aggregation.lookup(
                "addresses",
                "_id",
                "personId",
                "addresses"
            )
        )
        return mongoTemplate.aggregate(aggregation, "people", PersonWithAddresses::class.java)
            .next()
    }
}

// DTO to hold Person and their Addresses
data class PersonWithAddresses(
    val id: String,
    val name: String,
    val email: String,
    val age: Int,
    val addresses: List<Address>
)
