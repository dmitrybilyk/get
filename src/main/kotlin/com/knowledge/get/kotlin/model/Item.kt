package com.knowledge.get.kotlin.model

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@Document("items")
data class Item (
    val id: String? = null,
    @field:NotNull @field:NotBlank val name: String? = null,
    @field:NotNull @field:Positive val price: Double,
    val producerId: ObjectId? = null,
    val embeddedManufacturer: EmbeddedManufacturer? = null
)

data class EmbeddedManufacturer (
    val name: String,
    val country: String
)

data class ItemDto(
    val id: String? = null,
    val name: String? = null,
    val price: Double,
    val producerId: String? = null
)


fun Item.toDto(): ItemDto = ItemDto(
    id = this.id,
    name = this.name,
    price = this.price,
    producerId = this.producerId?.toHexString(),
)