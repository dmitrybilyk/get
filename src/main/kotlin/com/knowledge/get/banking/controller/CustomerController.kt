package com.knowledge.get.banking.controller

import com.knowledge.get.banking.model.Customer
import com.knowledge.get.banking.model.dto.CreateCustomerRequest
import com.knowledge.get.banking.model.dto.CustomerWithAccountsDto
import com.knowledge.get.banking.service.CustomerService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/api/customers")
class CustomerController(
    private val customerService: CustomerService
) {
    @PostMapping
    fun createCustomer(@Valid @RequestBody createCustomerRequest: CreateCustomerRequest): Mono<Customer> {
        val customer = Customer(name = createCustomerRequest.name, email = createCustomerRequest.email)
        return customerService.createCustomer(customer)
    }

    @GetMapping
    fun getCustomers(): Flux<Customer> = customerService.getCustomers()

    @GetMapping("/{id}")
    fun getCustomer(@PathVariable id: String): Mono<Customer> =
        customerService.getCustomerById(id)

    @GetMapping("/{id}/accounts")
    fun getCustomerWithAccounts(@PathVariable id: String): Mono<CustomerWithAccountsDto> =
        customerService.getCustomerWithAccounts(id)
}

