package com.knowledge.get.banking.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "customers")
data class Customer(
    @Id val id: String? = null,
    val name: String? = null,
    val email: String
)