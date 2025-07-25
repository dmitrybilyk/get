package com.knowledge.get.kotlin.model

import org.springframework.data.mongodb.core.mapping.Document

@Document("items")
data class Item (
    val id: String? = null,

    val name: String,

    val price: Double
)