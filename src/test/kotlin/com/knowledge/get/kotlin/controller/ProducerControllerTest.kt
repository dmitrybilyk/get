package com.knowledge.get.kotlin.controller

import com.knowledge.get.kotlin.model.Producer
import com.knowledge.get.kotlin.service.ProducerService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@WebFluxTest(controllers = [ProducerController::class])
@AutoConfigureWebTestClient
class ProducerControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockBean
    lateinit var producerService: ProducerService

    private val dummyProducer = Producer(
        id = "p1",
        name = "Test Producer",
        country = "Ukraine",
        address = "Kyiv"
    )

    @Test
    fun `createProducer should return 201 Created`() {
        whenever(producerService.save(any())).thenReturn(Mono.just(dummyProducer))

        webTestClient.post()
            .uri("/producers")
            .bodyValue(dummyProducer)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.data.name").isEqualTo("Test Producer")
    }

    @Test
    fun `getProducerById should return 200 with producer`() {
        whenever((producerService.getProducerById("p1"))).thenReturn(Mono.just(dummyProducer))

        webTestClient.get()
            .uri("/producers/p1")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data.id").isEqualTo("p1")
    }

    @Test
    fun `getProducers should return paged response`() {
        val page = PageImpl(listOf(dummyProducer))
        whenever((producerService.getProducers(0, 10))).thenReturn(Mono.just(page))

        webTestClient.get()
            .uri("/producers?page=0&size=10")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data.content[0].name").isEqualTo("Test Producer")
    }

    @Test
    fun `updateProducer should return updated producer`() {
        val updated = dummyProducer.copy(name = "Updated Producer")
        whenever((producerService.updateProducer("p1", updated))).thenReturn(Mono.just(updated))

        webTestClient.put()
            .uri("/producers/p1")
            .bodyValue(updated)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data.name").isEqualTo("Updated Producer")
    }

    @Test
    fun `deleteProducer should return 204 No Content`() {
        whenever((producerService.deleteById("p1"))).thenReturn(Mono.empty())

        webTestClient.delete()
            .uri("/producers?id=p1")
            .exchange()
            .expectStatus().isNoContent
    }
}
