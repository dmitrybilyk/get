package com.knowledge.get.kotlin.repository

import com.knowledge.get.kotlin.model.Producer
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProducerRepository : ReactiveMongoRepository<Producer, String> {

}