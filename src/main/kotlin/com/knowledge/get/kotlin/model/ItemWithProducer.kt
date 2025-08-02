package com.knowledge.get.kotlin.model

data class ItemWithProducer(
    val id: String?,
    val name: String,
    val price: Double,
    val producer: Producer
)
