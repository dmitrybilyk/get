package com.knowledge.get.banking.repository

import com.knowledge.get.banking.model.BankAccount
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface BankAccountRepository : ReactiveCrudRepository<BankAccount, String> {
    fun findByCustomerId(customerId: String): Flux<BankAccount>
}
