package com.knowledge.get.banking.controller

import com.knowledge.get.banking.model.BankAccount
import com.knowledge.get.banking.service.AccountService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.core.publisher.Flux
import java.math.BigDecimal

@RestController
@RequestMapping("/api/accounts")
class AccountController(
    private val accountService: AccountService
) {
    @PostMapping
    fun createAccount(
        @RequestParam customerId: String,
        @RequestParam initialBalance: BigDecimal,
        @RequestParam currency: String
    ): Mono<BankAccount> =
        accountService.createAccount(customerId, initialBalance, currency)

    @GetMapping("/by-customer/{customerId}")
    fun getAccountsForCustomer(@PathVariable customerId: String): Flux<BankAccount> =
        accountService.getAccountsForCustomer(customerId)
}
