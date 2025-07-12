package com.knowledge.get.repository

import com.knowledge.get.model.Address
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface AddressRepository: ReactiveMongoRepository<Address, String>