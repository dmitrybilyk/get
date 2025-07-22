package com.knowledge.get.kotlin.exception.handler

import com.knowledge.get.kotlin.exception.ItemNotFoundException
//import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ItemNotFoundException::class)
    fun handleNotFound(e: ItemNotFoundException): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)

//    @ExceptionHandler(ConstraintViolationException::class)
//    fun handleValidationError(e: ConstraintViolationException): ResponseEntity<String> =
//        ResponseEntity.badRequest().body(e.message ?: "Validation failed")

    @ExceptionHandler(Exception::class)
    fun handleGenericError(e: Exception): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message ?: "Unexpected error")
}
