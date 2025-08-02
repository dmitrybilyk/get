package com.knowledge.get.kotlin.controller

import com.knowledge.get.kotlin.controller.response.ApiResponse
import com.knowledge.get.kotlin.model.Item
import com.knowledge.get.kotlin.model.ItemWithProducer
import com.knowledge.get.kotlin.model.Producer
import com.knowledge.get.kotlin.service.ItemService
import com.knowledge.get.kotlin.service.ProducerService
import org.springframework.data.domain.PageImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/items")
class ItemController(
    private val service: ItemService,
    private val producerService: ProducerService
) {

    @PostMapping
    fun createItem(@Valid @RequestBody item: Item): Mono<ResponseEntity<ApiResponse<Item>>> {
        return service.save(item)
            .map { savedItem ->
                val response: ApiResponse<Item> = ApiResponse.Success(savedItem)
                ResponseEntity.ok(response)
            }
        // we handle exceptions in GlobalExceptionHandler
//            .onErrorResume { e ->
//                val error = ApiResponse.Error("Failed to save item: ${e.message}")
//                Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error))
//            }
    }

    @PostMapping("/producer")
    fun createProducer(@Valid @RequestBody producer: Producer): Mono<ResponseEntity<ApiResponse<Producer>>> {
        return producerService.save(producer)
            .map { savedProducer ->
                ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.Success(savedProducer))
            }
    }

    @PostMapping("/with-producer")
    fun createItemWithProducer(@Valid @RequestBody item: Item): Mono<ResponseEntity<ApiResponse<Item>>> {
        return producerService.getProducerById(item.producerId!!.toString())
            .switchIfEmpty(Mono.error(IllegalArgumentException("Producer not found")))
            .flatMap {
                service.save(item)
            }
            .map { savedItem ->
                val response: ApiResponse<Item> = ApiResponse.Success(savedItem)
                ResponseEntity.ok(response)
            }
            .onErrorResume { e ->
                val error = ApiResponse.Error("Failed to create item: ${e.message}")
                Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error))
            }
    }

    @GetMapping("/{id}/with-producer")
    fun getItemWithProducer(@PathVariable id: String): Mono<ResponseEntity<ApiResponse<ItemWithProducer>>> {
        return service.findItemWithProducerById(id)
            .map { ResponseEntity.ok(ApiResponse.Success(it)) }
    }

    @GetMapping("/with-producer")
    fun getItemsWithProducer(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Mono<ResponseEntity<ApiResponse<PageImpl<ItemWithProducer>>>> {
        return service.findAllItemsWithProducers(page, size)
            .map { ResponseEntity.ok(ApiResponse.Success(it)) }
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

    @GetMapping("/search/manufacturer")
    fun findByManufacturer(@RequestParam name: String): Flux<Item> {
        return service.findByManufacturer(name)
    }

}