package com.knowledge.get.banking.exception

class BankAccountException(
    val errorCode: String,
    message: String,
    val details: String? = null
) : RuntimeException(message)