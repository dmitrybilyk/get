package com.knowledge.get.banking.service

import com.knowledge.get.banking.model.BankAccount
import com.knowledge.get.banking.repository.BankAccountRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.Flux
import java.math.BigDecimal

@Service
class AccountService(
    private val accountRepo: BankAccountRepository
) {
    fun createAccount(customerId: String, initialBalance: BigDecimal, currency: String): Mono<BankAccount> =
        accountRepo.save(
            BankAccount(
                customerId = customerId,
                balance = initialBalance,
                currency = currency
            )
        )

    fun getAccountsForCustomer(customerId: String): Flux<BankAccount> =
        accountRepo.findByCustomerId(customerId)
}
