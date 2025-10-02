//package com.knowledge.get
//
//import com.knowledge.get.model.Person
//import com.knowledge.get.repository.PersonRepository
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.TestInstance
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.http.MediaType
//import org.springframework.test.web.reactive.server.WebTestClient
//import reactor.core.publisher.Flux
//import reactor.kotlin.core.publisher.toMono
//import kotlin.test.Test
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebTestClient
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class PersonReactiveIntegrationTest {
//
//    @Autowired
//    lateinit var webTestClient: WebTestClient
//
//    @Autowired
//    lateinit var repository: PersonRepository
//
//    @BeforeEach
//    fun setup() {
//        repository.deleteAll().block()
//    }
//
//    @Test
//    fun `create person and get it`() {
//        val person = Person(name = "Alice", email = "alice@example.com", age = 20)
//
//        val created = webTestClient.post()
//            .uri("/people")
//            .contentType(MediaType.APPLICATION_JSON)
//            .bodyValue(person)
//            .exchange()
//            .expectStatus().isOk
//            .expectBody(Person::class.java)
//            .returnResult()
//            .responseBody!!
//
//        webTestClient.get()
//            .uri("/people/${created.id}")
//            .exchange()
//            .expectStatus().isOk
//            .expectBody()
//            .jsonPath("$.name").isEqualTo("Alice")
//    }
//
//    @Test
//    fun `get all people`() {
//        val people = listOf(
//            Person(name = "Alice", email = "alice@example.com", age = 20),
//            Person(name = "Bob", email = "bob@example.com", age = 20)
//        )
//
//        repository.saveAll(people).collectList().block()
//
//        webTestClient.get()
//            .uri("/people")
//            .exchange()
//            .expectStatus().isOk
//            .expectBodyList(Person::class.java)
//            .hasSize(2)
//    }
//
//    @Test
//    fun `get person not found`() {
//        webTestClient.get()
//            .uri("/people/nonexistent-id")
//            .exchange()
//            .expectStatus().isNotFound
//    }
//
//    @Test
//    fun `delete person`() {
//        val person = repository.save(Person(name = "Alice", email = "alice@example.com", age = 20)).block()!!
//
//        webTestClient.delete()
//            .uri("/people/${person.id}")
//            .exchange()
//            .expectStatus().isNoContent
//
//        // Confirm deletion
//        webTestClient.get()
//            .uri("/people/${person.id}")
//            .exchange()
//            .expectStatus().isNotFound
//    }
//
//    @Test
//    fun `should handle concurrent requests asynchronously`() {
//        val requests = (1..20).map {
//            webTestClient.post()
//                .uri("/people")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(Person(name = "User$it", email = "user$it@test.com", age = 20))
//                .exchange()
//                .toMono()
//        }
//
//        Flux.merge(requests)
//            .collectList()
//            .block()
//
//        val all = repository.findAll().collectList().block()
//        assertEquals(20, all?.size)
//    }
//}
