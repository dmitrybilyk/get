package com.knowledge.get.kotlin.webclient

import com.knowledge.get.kotlin.controller.response.ApiResponse
import com.knowledge.get.kotlin.model.Item
import com.knowledge.get.kotlin.model.ItemWithProducer
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.Thread.sleep

@Component
class ItemClient(private val webClient: WebClient) {

    fun getItemById(id: String): Mono<Item> {
        val responseType = object : ParameterizedTypeReference<ApiResponse.Success<Item>>() {}
        return webClient.get()
            .uri("/items/{id}", id)
            .retrieve()
            .bodyToMono(responseType)
            .map { it.data }
    }

    fun createItem(item: Item): Mono<Item> {
        val responseType = object : ParameterizedTypeReference<ApiResponse.Success<Item>>() {}
        return webClient.post()
            .uri("/items")
            .bodyValue(item)
            .retrieve()
            .onStatus({ it.is5xxServerError }) { response ->
                response.bodyToMono(String::class.java).flatMap { body ->
                    Mono.error(RuntimeException("Server error: $body"))
                }
            }
            .bodyToMono(responseType)
            .map { it.data }
    }
}

fun main() {
//    getItem("6894b6b27da0fd2d371456dd")
//        .subscribe { println(it) }

    getAllItems()
        .subscribe { println(it) }

//    createItem(Item(null, "ENRICH_FAIL", 40.0))
//        .subscribe { println(it) }

    sleep(3000)
}

val client = WebClient.create("http://localhost:8081")

fun getItem(id: String): Mono<Item> {
    val responseType = object : ParameterizedTypeReference<ApiResponse.Success<Item>>() {}
    return client.get()
        .uri("/items/{id}", id)
        .retrieve()
        .bodyToMono(responseType)
        .map { it.data }
}

fun getAllItems(): Flux<Item> {
    val type = object : ParameterizedTypeReference<Item>() {}

    return client.get()
        .uri("/items/plain")
        .retrieve()
        .bodyToFlux(type)
}

fun createItem(item: Item): Mono<Item> {
    val responseType = object : ParameterizedTypeReference<ApiResponse.Success<Item>>() {}

    return client.post()
        .uri("/items")
        .bodyValue(item)
        .retrieve()
        .onStatus( { it.is5xxServerError }, { response ->
            response.bodyToMono(String::class.java).flatMap { errorResponse ->
                Mono.error(RuntimeException("Server error: $errorResponse"))
            }
        })
        .bodyToMono(responseType)
        .doOnSuccess{
            println(it)
        }
        .doOnError{
            println(it)
        }
        .map { it.data }
}

