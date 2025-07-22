package com.knowledge.get.kotlin.service

import com.knowledge.get.kotlin.model.Item
import com.knowledge.get.kotlin.repository.ItemRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ItemService(private val repository: ItemRepository) {

    fun save(item: Item) = repository.save(item)

    fun findAll(): Flux<Item> = repository.findAll()

    fun findById(id: String): Mono<Item> = repository.findById(id)
}