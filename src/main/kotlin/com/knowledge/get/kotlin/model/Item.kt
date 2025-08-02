package com.knowledge.get.kotlin.model

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank

@Document("items")
data class Item (
    val id: String? = null,
    @field:NotBlank val name: String? = null,
    val price: Double,
    val producerId: ObjectId? = null,
    val embeddedManufacturer: EmbeddedManufacturer? = null
)

data class EmbeddedManufacturer (
    val name: String,
    val country: String
)