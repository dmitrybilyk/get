package com.knowledge.get.banking.model.dto

import java.math.BigDecimal

data class CustomerBalanceSummary(
    val customerId: String,
    val currency: String,
    val totalBalance: BigDecimal
)