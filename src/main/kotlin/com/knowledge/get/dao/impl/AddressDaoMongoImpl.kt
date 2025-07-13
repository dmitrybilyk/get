package com.knowledge.get.dao.impl

import com.knowledge.get.dao.GenericDao
import com.knowledge.get.dao.api.AddressDao
import com.knowledge.get.model.Address
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux


@Repository
class AddressDaoMongoImpl(
    private val mongoTemplate: ReactiveMongoTemplate
) : GenericDao<Address>(Address::class.java), AddressDao {

    override fun doGetAll(): Flux<Address> {
        // Add some custom behavior here
        println("Executing custom logic for AddressDao")

        val query = Query() // could be built dynamically
        return mongoTemplate.find(query, Address::class.java)
    }
}