package com.knowledge.get.banking.service

import com.knowledge.get.banking.exception.BankAccountException
import com.knowledge.get.banking.model.BankAccount
import com.knowledge.get.banking.model.dto.CustomerBalanceSummary
import com.knowledge.get.banking.model.dto.TopCustomerSummary
import com.knowledge.get.banking.repository.BankAccountRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.Flux
import java.math.BigDecimal
import java.util.regex.*

@Service
class AccountService (
    private val accountRepo: BankAccountRepository
) {
    // Validate customerId format (assuming MongoDB ObjectId)
    private val customerIdPattern = Pattern.compile("[a-zA-Z0-9]{24}")
    // Validate ISO 4217 currency code
    private val currencyPattern = Pattern.compile("[A-Z]{3}")

    fun createAccount(customerId: String, initialBalance: BigDecimal, currency: String): Mono<BankAccount> =
        accountRepo.save(
            BankAccount(
                customerId = customerId,
                balance = initialBalance,
                currency = currency
            )
        )

    fun getCustomersAccounts(customerId: String): Flux<BankAccount> =
        accountRepo.findByCustomerId(customerId)

    fun getByCustomerIdAndCurrency(customerId: String, currency: String): Flux<BankAccount> =
        accountRepo.findByCustomerIdAndCurrency(customerId, currency)

    fun getTotalBalanceByCustomerAndOptionalCurrency(customerId: String, currency: String?): Flux<CustomerBalanceSummary> {
        validateCustomerId(customerId)
        currency?.let { validateCurrency(it) }
        val currencyFilter = currency?.let { mapOf("currency" to it) }
        return accountRepo.getTotalBalanceByCustomerAndOptionalCurrency(customerId, currencyFilter)
            .switchIfEmpty(Mono.error(NoSuchElementException("No accounts found for customer $customerId${currency?.let { " and currency $it" } ?: ""}")))
    }

    fun getTopCustomersByBalance(currency: String?, limit: Int): Flux<TopCustomerSummary> {
        if (limit <= 0) {
            return Flux.error(
                BankAccountException(
                    errorCode = "INVALID_LIMIT",
                    message = "Limit must be a positive integer",
                    details = "Provided limit: $limit"
                )
            )
        }
        currency?.let { validateCurrency(it) }
        val currencyFilter = currency?.let { mapOf("currency" to it) }
        return accountRepo.getTopCustomersByBalance(currencyFilter, limit)
            .switchIfEmpty(Mono.error(NoSuchElementException("No accounts found${currency?.let { " for currency $it" } ?: ""}")))
    }

    private fun validateCustomerId(customerId: String) {
        if (!customerIdPattern.matcher(customerId).matches()) {
            throw IllegalArgumentException("Invalid customer ID format")
        }
    }

    private fun validateCurrency(currency: String) {
        if (!currencyPattern.matcher(currency).matches()) {
            throw IllegalArgumentException("Currency must be a valid ISO 4217 code (e.g., USD)")
        }
    }
}
