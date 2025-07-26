package com.knowledge.get.banking.exception

class CustomerNotFoundException(val customerId: String) :
    RuntimeException("Customer not found with id: $customerId")
