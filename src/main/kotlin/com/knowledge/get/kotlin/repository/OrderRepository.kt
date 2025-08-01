package com.knowledge.get.kotlin.repository

import com.knowledge.get.kotlin.model.Order
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface OrderRepository : ReactiveMongoRepository<Order, String>