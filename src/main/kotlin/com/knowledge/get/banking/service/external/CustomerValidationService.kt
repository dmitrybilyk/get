package com.knowledge.get.banking.service.external

import com.knowledge.get.banking.model.Customer
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class CustomerValidationService {

    fun validateCustomer(customer: Customer) : Mono<Boolean> {
        return Mono.just(customer)
            .flatMap { cust ->
                Mono.just(!cust.name!!.contains("invalid"))
                    .delayElement(Duration.ofSeconds(1))
            }
    }

}