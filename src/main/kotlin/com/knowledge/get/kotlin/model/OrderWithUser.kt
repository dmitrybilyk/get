package com.knowledge.get.kotlin.model

data class OrderWithUser(
    val id: String?,
    val userId: String,
    val total: Double,
    val items: List<String>,
    val user: User
)