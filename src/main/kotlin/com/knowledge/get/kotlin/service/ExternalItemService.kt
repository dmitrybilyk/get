package com.knowledge.get.kotlin.service

import com.knowledge.get.kotlin.model.Item
import reactor.core.publisher.Mono

interface ExternalItemService {
    fun enrichItem(item: Item): Mono<Item>
}