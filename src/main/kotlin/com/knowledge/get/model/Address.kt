package com.knowledge.get.model

import org.springframework.data.mongodb.core.mapping.Document

@Document(collation = "addresses")
data class Address(
    val id: String,
    val personId: String,
    val street: String,
    val city: String,
    val zipCode: String
) {
}