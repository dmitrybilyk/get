package com.knowledge.get.banking.repository

import com.knowledge.get.banking.model.Customer
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface CustomerRepository : ReactiveCrudRepository<Customer, String>
