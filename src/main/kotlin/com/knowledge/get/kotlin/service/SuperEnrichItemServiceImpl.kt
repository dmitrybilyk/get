package com.knowledge.get.kotlin.service

import com.knowledge.get.kotlin.model.Item
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Duration

@Service
@Primary
class SuperEnrichItemServiceImpl : EnrichItemService {
    override fun enrichItem(item: Item): Mono<Item> {
        return Mono.just(item.copy(name = "Super Enriched for ${item.name}"))
            .delayElement(Duration.ofMillis(300))
            .doOnNext { println("Super Enriched for ${item.name}") }
    }
}