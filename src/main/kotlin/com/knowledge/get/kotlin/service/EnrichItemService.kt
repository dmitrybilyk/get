package com.knowledge.get.kotlin.service

import com.knowledge.get.kotlin.model.Item
import reactor.core.publisher.Mono

interface EnrichItemService {
    fun enrichItem(item: Item): Mono<Item>
}