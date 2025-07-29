package com.knowledge.get.sandbox.model

data class User(
    override val id: String,
    val name: String,
    val email: String
) : BaseEntity
