package com.knowledge.get.sandbox.model

data class Book (
    override val id: String,
    val title: String,
    val author: String
): BaseEntity