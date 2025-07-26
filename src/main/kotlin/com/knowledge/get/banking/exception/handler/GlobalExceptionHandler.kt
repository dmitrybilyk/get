package com.knowledge.get.banking.exception.handler

import com.knowledge.get.banking.exception.CustomerNotFoundException
import org.springframework.core.codec.DecodingException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(DecodingException::class)
    fun handleDecodingException(ex: DecodingException): ResponseEntity<Map<String, String>> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(mapOf("error" to "Invalid request format: ${ex.mostSpecificCause.message}"))

    @ExceptionHandler(CustomerNotFoundException::class)
    fun handleCustomerNotFound(ex: CustomerNotFoundException): ResponseEntity<Map<String, String?>> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            mapOf(
                "error" to ex.message,
                "customerId" to ex.customerId
            )
        )

}
