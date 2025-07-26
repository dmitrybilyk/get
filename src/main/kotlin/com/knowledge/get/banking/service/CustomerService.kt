package com.knowledge.get.banking.service

import com.knowledge.get.banking.exception.CustomerNotFoundException
import com.knowledge.get.banking.exception.CustomerValidationException
import com.knowledge.get.banking.model.Customer
import com.knowledge.get.banking.model.dto.CustomerWithAccountsDto
import com.knowledge.get.banking.repository.BankAccountRepository
import com.knowledge.get.banking.repository.CustomerRepository
import com.knowledge.get.banking.service.external.CustomerValidationService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class CustomerService (
    private val customerRepository: CustomerRepository,
    private val bankAccountRepository: BankAccountRepository,
    private val customerValidationService: CustomerValidationService
) {

    fun createCustomer(customer: Customer): Mono<Customer> =
        customerRepository.save(customer)

    fun getCustomerById(customerId: String): Mono<Customer> =
        customerRepository.findById(customerId)
            .switchIfEmpty { Mono.error(CustomerNotFoundException(customerId)) }
            .flatMap { customer ->
                customerValidationService.validateCustomer(customer)
                    .flatMap { isValid ->
                        if (isValid) Mono.just(customer)
                        else Mono.error(CustomerValidationException("Customer validation failed"))
                    }
            }

    fun getCustomer1(customerId: String): Mono<Customer> =
        customerRepository.findById(customerId)
            .switchIfEmpty { Mono.error(CustomerNotFoundException(customerId)) }
            .flatMap { customer ->
                customerValidationService.validateCustomer(customer)
                    .flatMap { isValid ->
                        if (isValid) {
                            Mono.just(customer)
                        } else Mono.error(CustomerValidationException("Customer validation failed"))
                    }
            }


    fun getCustomerWithAccounts(customerId: String): Mono<CustomerWithAccountsDto> =
        customerRepository.findById(customerId)
            .switchIfEmpty { Mono.error(CustomerNotFoundException(customerId)) }
            .flatMap { customer ->
                bankAccountRepository.findByCustomerId(customer.id!!)
                    .collectList()
                    .map { accounts -> CustomerWithAccountsDto(customer, accounts) }
            }

    fun getCustomers(): Flux<Customer> = customerRepository.findAll()
        .filterWhen {
            customerValidationService.validateCustomer(it)
        }

}