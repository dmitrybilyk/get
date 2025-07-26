package com.knowledge.get.banking.exception

class CustomerValidationException(val customerId: String) :
    RuntimeException("Customer is not valid with id: $customerId")
