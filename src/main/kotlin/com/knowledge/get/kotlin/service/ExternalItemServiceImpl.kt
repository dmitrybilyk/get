package com.knowledge.get.kotlin.service

import com.knowledge.get.kotlin.model.Item
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class ExternalItemServiceImpl : ExternalItemService {
    override fun enrichItem(item: Item): Mono<Item> {
        return Mono.just(item.copy(name = "Enriched for ${item.name}"))
            .delayElement(Duration.ofMillis(300))
            .doOnNext { println("Enriched for ${item.name}") }
    }
}