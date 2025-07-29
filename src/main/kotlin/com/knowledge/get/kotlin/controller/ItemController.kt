package com.knowledge.get.kotlin.controller

import com.knowledge.get.kotlin.controller.response.ApiResponse
import com.knowledge.get.kotlin.model.Item
import com.knowledge.get.kotlin.service.ItemService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/items")
class ItemController(private val service: ItemService) {

    @PostMapping
    fun createItem(@RequestBody item: Item): Mono<Item> {
        return service.save(item)
    }

    @GetMapping
    fun getAllItems(): Flux<Item> = service.findAll()

    @GetMapping("/{id}")
    fun getItemById(@PathVariable id: String): Mono<ApiResponse<Item>> = service.findById(id)
        .map<ApiResponse<Item>> { ApiResponse.Success(it) }
        .onErrorResume { Mono.just(ApiResponse.Error(it.message ?: "Unknown error")) }

    @GetMapping("/search/price")
    fun getItemsByPriceRange(@RequestParam from: Double, @RequestParam to: Double, @RequestParam page: Int): Flux<Item> {
        return service.findByPriceRange(from, to, page)
    }

}