package com.knowledge.get.kotlin.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("orders")
data class Order(
    @Id
    val id: String? = null,
    val userId: String,
    val total: Double,
    val items: List<String> = listOf()
)