package com.knowledge.get

import com.knowledge.get.model.Person
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.Instant

@SpringBootTest
@AutoConfigureWebTestClient
class ConcurrentRequestsTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `should return 10 enriched slow persons concurrently`() {
        // Increase timeout to 30 seconds
        val webTestClientWithTimeout = webTestClient.mutate()
            .responseTimeout(Duration.ofSeconds(90))
            .build()

        val ids = (1..10).map { it.toString() }

        val requests = ids.map { id ->
            webTestClientWithTimeout.get()
                .uri("/people/slow/$id")
                .exchange()
                .expectStatus().isOk
                .returnResult(Person::class.java)
                .responseBody
                .single()
                .map { person ->
                    assertEquals("User $id ✅ enriched", person.name)
//                    assertEquals("User $id ✅ enriched enhanced", person.name)
                    assertEquals("user$id@example.com", person.email)
                    assertEquals(30, person.age)
                }
        }

        // Merge all requests to run concurrently
        val start = Instant.now()
        Flux.merge(requests)
//        Flux.concat(requests)
            .then()
            .block()
        val end = Instant.now()
        val duration = Duration.between(start, end)
        println("Test execution took ${duration.toMillis()} ms (${duration.toSeconds()} seconds)")
    }


}