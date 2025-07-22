package com.knowledge.get.kotlin.model

import org.springframework.data.mongodb.core.mapping.Document

@Document("items")
data class Item (
    val id: String? = null,

//    @field:NotBlank(message = "Name must not be blank")
    val name: String,

//    @field:Min(value = 0, message = "Price must be non-negative")
    val price: Double
)