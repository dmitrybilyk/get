package com.knowledge.get.banking.model.dto

import java.math.BigDecimal

data class TopCustomerSummary(
    val customerId: String,
    val customerName: String?,
    val customerEmail: String,
    val currency: String,
    val totalBalance: BigDecimal
)