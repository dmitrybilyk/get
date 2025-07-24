package com.knowledge.get.kotlin

import com.knowledge.get.kotlin.controller.ItemController
import com.knowledge.get.kotlin.model.Item
import com.knowledge.get.kotlin.service.ItemService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.bean.override.mockito.MockitoBean
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(controllers = [ItemController::class])
class ItemControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockitoBean
    lateinit var itemService: ItemService

    @Test
    fun `should return all items`() {
        val items = listOf(
            Item(id = "1", name = "Item A", price = 10.0),
            Item(id = "2", name = "Item B", price = 20.0)
        )

        `when`(itemService.findAll()).thenReturn(Flux.fromIterable(items))

        webTestClient.get()
            .uri("/items")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Item::class.java)
            .hasSize(2)
            .contains(*items.toTypedArray())
    }

    @Test
    fun `should return item by id`() {
        val item = Item(id = "1", name = "Item A", price = 10.0)

        `when`(itemService.findById("1")).thenReturn(Mono.just(item))

        webTestClient.get()
            .uri("/items/1")
            .exchange()
            .expectStatus().isOk
            .expectBody(Item::class.java)
            .isEqualTo(item)
    }

    @Test
    fun `should save item`() {
        val input = Item(name = "Item X", price = 99.0)
        val saved = input.copy(id = "123")

        `when`(itemService.save(input)).thenReturn(Mono.just(saved))

        webTestClient.post()
            .uri("/items")
            .bodyValue(input)
            .exchange()
            .expectStatus().isOk
            .expectBody(Item::class.java)
            .isEqualTo(saved)
    }
}
