//package com.knowledge.get.kotlin
//
//import com.knowledge.get.kotlin.model.Item
//import com.knowledge.get.kotlin.repository.ItemRepository
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
//import org.springframework.test.context.DynamicPropertyRegistry
//import org.springframework.test.context.DynamicPropertySource
//import org.testcontainers.containers.MongoDBContainer
//import org.testcontainers.junit.jupiter.Container
//import org.testcontainers.junit.jupiter.Testcontainers
//import reactor.test.StepVerifier
//import java.time.Duration
//
//@Testcontainers
//@SpringBootTest
//@ActiveProfiles("test")
//class ItemRepositoryTest {
//
//    companion object {
//        @Container
//        val mongoDBContainer = MongoDBContainer("mongo:7.0")
//            .withReuse(true)
//            .withStartupTimeout(Duration.ofSeconds())
//            .withLogConsumer(output -> println(output.utf8String))
//
//        @JvmStatic
//        @DynamicPropertySource
//        fun mongoProperties(registry: DynamicPropertyRegistry) {
//            registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl)
//        }
//    }
//
//    @Autowired
//    lateinit var repository: ItemRepository
//
//    @Test
//    fun `save and retrieve item`() {
//        val item = Item(name = "Mouse", price = 20.0)
//        val saved = repository.save(item)
//
//        StepVerifier.create(saved.flatMap { repository.findById(it.id!!) })
//            .expectNextMatches { it.name == "Mouse" && it.price == 20.0 }
//            .verifyComplete()
//    }
//}
