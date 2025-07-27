package com.knowledge.get.banking.controller

import com.knowledge.get.banking.exception.CustomerNotFoundException
import com.knowledge.get.banking.model.BankAccount
import com.knowledge.get.banking.model.dto.CustomerBalanceSummary
import com.knowledge.get.banking.model.dto.TopCustomerSummary
import com.knowledge.get.banking.service.AccountService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import reactor.core.publisher.Flux
import java.math.BigDecimal
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("/accounts")
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

    @GetMapping("/customer/{customerId}")
    fun getCustomersAccounts(@PathVariable customerId: String): Flux<BankAccount> =
        accountService.getCustomersAccounts(customerId)
            .onErrorResume { ex ->
                when (ex) {
                    is CustomerNotFoundException -> Mono.error(
                        ResponseStatusException(
                            HttpStatus.NOT_FOUND, ex.message, ex
                        )
                    )

                    else -> {
                        logger.error("Unexpected error for customerId=$customerId", ex)
                        Mono.error(ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"))
                    }
                }
            }

    @GetMapping("/customer/{customerId}/currency/{currency}")
    fun getAccountsByCustomerAndCurrency(
        @PathVariable customerId: String,
        @PathVariable currency: String
    ): Mono<ResponseEntity<Flux<BankAccount>>> =
        accountService.getByCustomerIdAndCurrency(customerId, currency)
            .collectList()
            .map { accounts ->
                if (accounts.isEmpty()) {
                    ResponseEntity.notFound().build()
                } else {
                    ResponseEntity.ok(Flux.fromIterable(accounts))
                }
            }
            .onErrorResume {
                Mono.just(ResponseEntity.badRequest().build())
            }

    @GetMapping("/customer/{customerId}/total-balance")
    fun getTotalBalanceByCustomer(
        @PathVariable @Pattern(regexp = "[a-zA-Z0-9]{24}", message = "Invalid customer ID format") customerId: String,
        @RequestParam(required = false) @Pattern(
            regexp = "[A-Z]{3}",
            message = "Currency must be a valid ISO 4217 code"
        ) currency: String?
    ): Mono<ResponseEntity<Flux<CustomerBalanceSummary>>> {
        return accountService.getTotalBalanceByCustomerAndOptionalCurrency(customerId, currency)
            .collectList()
            .map { summaries ->
                if (summaries.isEmpty()) ResponseEntity.notFound().build()
                else ResponseEntity.ok(Flux.fromIterable(summaries))
            }
//            .onErrorResume(BankAccountException::class.java) { ex ->
//                Mono.just(ResponseEntity.status(
//                    if (ex.errorCode == "ACCOUNT_NOT_FOUND") HttpStatus.NOT_FOUND else HttpStatus.BAD_REQUEST)
//                    .body(Flux.just(ErrorResponse(ex.errorCode, ex.message ?: "Unknown error", ex.details, LocalDateTime.now())))
//            }
    }

    //    @Operation(summary = "Get top customers by total balance",
//        description = "Retrieves the top N customers by total balance, optionally filtered by currency")
//    @ApiResponses(
//        ApiResponse(responseCode = "200", description = "Successfully retrieved top customers"),
//        ApiResponse(responseCode = "400", description = "Invalid currency or limit"),
//        ApiResponse(responseCode = "404", description = "No accounts found for the given criteria")
//    )
    @GetMapping("/top-customers")
    fun getTopCustomersByBalance(
        @RequestParam(required = false) @Pattern(
            regexp = "[A-Z]{3}",
            message = "Currency must be a valid ISO 4217 code"
        ) currency: String?,
        @RequestParam(defaultValue = "10") limit: Int
    ): Mono<ResponseEntity<Flux<TopCustomerSummary>>> {
        return accountService.getTopCustomersByBalance(currency, limit)
            .collectList()
            .map { summaries ->
                if (summaries.isEmpty()) ResponseEntity.notFound().build()
                else ResponseEntity.ok(Flux.fromIterable(summaries))
            }
//            .onErrorResume(BankAccountException::class.java) { ex ->
//                Mono.just(ResponseEntity.status(
//                    if (ex.errorCode == "ACCOUNT_NOT_FOUND") HttpStatus.NOT_FOUND else HttpStatus.BAD_REQUEST)
//                    .body(Flux.just(ErrorResponse(ex.errorCode, ex.message ?: "Unknown error", ex.details, LocalDateTime.now())))
//            }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(AccountController::class.java)
    }


}
