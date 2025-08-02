package com.knowledge.get.kotlin.rsocket

import com.knowledge.get.kotlin.model.Item
import com.knowledge.get.kotlin.model.ItemDto
import com.knowledge.get.kotlin.model.toDto
import com.knowledge.get.kotlin.repository.ItemRepository
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

//@MessageMapping("items.get.all")
@Controller
class ItemRSocketController(private val itemRepository: ItemRepository) {

    @MessageMapping("items.get.all")
    fun getAllItems(): Flux<ItemDto> = itemRepository.findAll()
        .map { item -> item.toDto() }

    @MessageMapping("items.create")
    fun createItem(item: Item): Mono<Item> = itemRepository.save(item)
}
