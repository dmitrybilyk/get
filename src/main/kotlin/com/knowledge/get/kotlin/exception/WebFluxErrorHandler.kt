//package com.knowledge.get.kotlin.exception
//
//import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.core.annotation.Order
//import org.springframework.core.codec.DecodingException
//import org.springframework.http.HttpStatus
//import org.springframework.http.MediaType
//import org.springframework.web.server.ServerWebExchange
//import org.springframework.web.server.WebExceptionHandler
//import reactor.core.publisher.Mono
//import java.nio.charset.StandardCharsets
//
//@Configuration
//class WebFluxErrorHandler {
//
//    @Bean
//    @Order(-2) // Make sure it runs before the default Spring handler
//    fun decodingExceptionHandler(): WebExceptionHandler {
//        return WebExceptionHandler { exchange: ServerWebExchange, ex: Throwable ->
//
//            val realCause = ex.cause ?: ex
//
//            if (realCause is MissingKotlinParameterException || realCause is DecodingException) {
//                val message = when (realCause) {
//                    is MissingKotlinParameterException -> {
//                        "Missing required field: ${realCause.parameter.name ?: "unknown"}"
//                    }
//                    else -> "Malformed JSON input"
//                }
//
//                val body = """{"message": "$message"}"""
//                val response = exchange.response
//                response.statusCode = HttpStatus.BAD_REQUEST
//                response.headers.contentType = MediaType.APPLICATION_JSON
//
//                val buffer = response.bufferFactory().wrap(body.toByteArray(StandardCharsets.UTF_8))
//                return@WebExceptionHandler response.writeWith(Mono.just(buffer))
//            }
//
//            Mono.error(ex) // delegate to default handler for other exceptions
//        }
//    }
//}
