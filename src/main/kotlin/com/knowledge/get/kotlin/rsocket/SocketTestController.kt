package com.knowledge.get.kotlin.rsocket

import com.knowledge.get.kotlin.model.Item
import org.springframework.http.ResponseEntity
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/socket")
class SocketTestController(private val rSocketRequesterMono: Mono<RSocketRequester>) {

    @GetMapping("/items")
    fun getItems(): Mono<ResponseEntity<String>> {
        return rSocketRequesterMono.flatMap { requester ->
            requester
                .route("items.get.all")
                .retrieveFlux(Item::class.java)
                .collectList()
                .map { items -> ResponseEntity.ok("Received items: $items") }
        }
    }
}