package com.knowledge.get.kotlin.repository

import com.knowledge.get.kotlin.model.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface UserRepository : ReactiveMongoRepository<User, String>