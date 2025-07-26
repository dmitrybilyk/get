package com.knowledge.get.banking.model.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class CreateCustomerRequest(
    @field:NotBlank(message = "Name is required") val name: String? = null,
    @field:Email val email: String
)

