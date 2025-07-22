package com.knowledge.get.kotlin.repository

import com.knowledge.get.kotlin.model.Item
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : ReactiveMongoRepository<Item, String>