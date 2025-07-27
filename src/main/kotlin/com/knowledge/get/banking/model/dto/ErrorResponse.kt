package com.knowledge.get.banking.model.dto

import java.time.LocalDateTime

data class ErrorResponse(
    val errorCode: String,
    val message: String,
    val details: String? = null,
    val timestamp: LocalDateTime = LocalDateTime.now()
)