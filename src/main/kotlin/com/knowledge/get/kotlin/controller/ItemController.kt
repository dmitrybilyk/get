package com.knowledge.get.kotlin.controller

import com.knowledge.get.kotlin.controller.response.ApiResponse
import com.knowledge.get.kotlin.model.Item
import com.knowledge.get.kotlin.model.ItemWithProducer
import com.knowledge.get.kotlin.model.ProducerItemCount
import com.knowledge.get.kotlin.service.ItemService
import org.springframework.data.domain.PageImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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
    private val itemService: ItemService
) {

    @PostMapping
    fun createItem(@Valid @RequestBody item: Item): Mono<ResponseEntity<ApiResponse<Item>>> {
        return itemService.save(item)
            .map { savedItem ->
                val response: ApiResponse<Item> = ApiResponse.Success(savedItem)
                ResponseEntity.ok(response)
            }
    }

    @GetMapping("/{id}")
    fun getItemById(@PathVariable id: String): Mono<ApiResponse<Item>> = itemService.findById(id)
        .map { ApiResponse.Success(it) }

    @GetMapping
    fun getAllItems(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Mono<ResponseEntity<ApiResponse<PageImpl<ItemWithProducer>>>> =
        itemService.findAllItemsWithProducers(page, size)
            .map { ResponseEntity.ok(ApiResponse.Success(it)) }

    @GetMapping("/learn-aggregation")
    fun getAllItemsByFilter(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Mono<ResponseEntity<ApiResponse<PageImpl<ItemWithProducer>>>> =
        itemService.findWithAggregation(page, size)
            .map { ResponseEntity.ok(ApiResponse.Success(it)) }

    @GetMapping("/learn-group")
    fun getProucerItemCount(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Mono<ResponseEntity<ApiResponse<PageImpl<ProducerItemCount>>>> =
        itemService.getProducerItemCount(page, size)
            .map { ResponseEntity.ok(ApiResponse.Success(it)) }

    @PutMapping("/{id}")
    fun updateItem(@PathVariable id: String, @Valid @RequestBody item: Item): Mono<ResponseEntity<ApiResponse<Item>>> {
        return itemService.updateItem(id, item)
            .map { ResponseEntity.ok(ApiResponse.Success(it)) }
    }

    @DeleteMapping("/{id}")
    fun deleteItem(@PathVariable id: String): Mono<ResponseEntity<ApiResponse<Unit>>> {
        return itemService.deleteById(id)
            .thenReturn(ResponseEntity.noContent().build())
    }



    @PostMapping("/with-producers")
    fun createItemWithProducer(@Valid @RequestBody item: Item): Mono<ResponseEntity<ApiResponse<Item>>> {
        return itemService.save(item)
            .map { savedItem ->
                val response: ApiResponse<Item> = ApiResponse.Success(savedItem)
                ResponseEntity.ok(response)
            }
    }

    @GetMapping("/{id}/with-producer")
    fun getItemWithProducer(@PathVariable id: String): Mono<ResponseEntity<ApiResponse<ItemWithProducer>>> {
        return itemService.findItemWithProducerById(id)
            .map { ResponseEntity.ok(ApiResponse.Success(it)) }
    }

    @GetMapping("/with-producer")
    fun getItemsWithProducer(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Mono<ResponseEntity<ApiResponse<PageImpl<ItemWithProducer>>>> {
        return itemService.findAllItemsWithProducers(page, size)
            .map { ResponseEntity.ok(ApiResponse.Success(it)) }
    }

    @GetMapping("/search/price")
    fun getItemsByPriceRange(@RequestParam from: Double, @RequestParam to: Double, @RequestParam page: Int): Flux<Item> {
        return itemService.findByPriceRange(from, to, page)
    }

    @GetMapping("/search/manufacturer")
    fun findByManufacturer(@RequestParam name: String): Flux<Item> {
        return itemService.findByManufacturer(name)
    }

}