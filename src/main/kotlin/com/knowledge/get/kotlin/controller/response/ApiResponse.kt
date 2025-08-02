package com.knowledge.get.kotlin.controller.response

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T): ApiResponse<T>()
    data class Error(val message: String?): ApiResponse<Nothing>()
}