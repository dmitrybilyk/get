package com.knowledge.get.kotlin.exception.handler

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.knowledge.get.kotlin.controller.response.ApiResponse
import com.knowledge.get.kotlin.exception.ItemNotFoundException
import com.knowledge.get.kotlin.exception.NonPositivePriceException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleGenericError(e: Exception): ResponseEntity<ApiResponse.Error> =
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.Error(e.message ?: "Unexpected error"))

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleValidationError(ex: IllegalArgumentException): ResponseEntity<ApiResponse.Error> {
        return ResponseEntity.badRequest().body(ApiResponse.Error(ex.message))
    }

    @ExceptionHandler(ItemNotFoundException::class)
    fun handleNotFoundError(ex: ItemNotFoundException): ResponseEntity<ApiResponse.Error> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.Error(ex.message))
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException): ResponseEntity<ApiResponse.Error> {
        return when (val cause = ex.mostSpecificCause) {
            is MissingKotlinParameterException -> {
                val field = cause.parameter.name ?: "unknown"
                val message = "Missing required field: $field"
                ResponseEntity.badRequest().body(ApiResponse.Error(message))
            }

            else -> {
                ResponseEntity.badRequest().body(
                    ApiResponse.Error("Malformed request: ${cause.message}")
                )
            }
        }
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleOtherErrors(ex: RuntimeException): ResponseEntity<ApiResponse.Error> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.Error("Internal error: ${ex.message}"))
    }

    @ExceptionHandler(NonPositivePriceException::class)
    fun handleNonPositivePriceError(ex: NonPositivePriceException): ResponseEntity<ApiResponse.Error> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.Error(ex.message))
    }

    @ExceptionHandler(WebExchangeBindException::class)
    fun handleValidationError(ex: WebExchangeBindException): ResponseEntity<ApiResponse.Error> {
        val firstError = ex.bindingResult.fieldErrors.firstOrNull()
        val message = firstError?.let {
            "${it.field}: ${it.defaultMessage}"
        } ?: "Validation error"
        return ResponseEntity.badRequest().body(ApiResponse.Error(message))
    }
}
