package com.knowledge.get.banking.model.dto

import com.knowledge.get.banking.model.BankAccount
import com.knowledge.get.banking.model.Customer

data class CustomerWithAccountsDto(
    val customer: Customer,
    val accounts: List<BankAccount>
)
