package com.knowledge.get.dao.api

import com.knowledge.get.model.Address
import reactor.core.publisher.Flux

interface AddressDao {
    fun getAll(): Flux<Address>
}