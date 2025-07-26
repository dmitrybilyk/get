package com.knowledge.get.banking.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document
data class BankAccount(
    @Id val id: String? = null,
    val customerId: String,
    val balance: BigDecimal = BigDecimal.ZERO,
    val currency: String = "USD"
)
