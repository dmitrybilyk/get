//package com.knowledge.get
//
//import org.junit.jupiter.api.*
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.http.MediaType
////import org.springframework.kafka.core.ConsumerFactory
//import org.springframework.kafka.test.utils.KafkaTestUtils
//import org.springframework.test.web.reactive.server.WebTestClient
//import org.testcontainers.containers.KafkaContainer
//import org.testcontainers.containers.MongoDBContainer
//import org.testcontainers.junit.jupiter.Testcontainers
//import org.testcontainers.utility.DockerImageName
//
//import com.knowledge.get.repository.PersonRepository
//import net.bytebuddy.utility.dispatcher.JavaDispatcher
//import org.springframework.boot.autoconfigure.kafka.KafkaProperties
//
//import org.springframework.test.context.DynamicPropertyRegistry
//import org.springframework.test.context.DynamicPropertySource
//
//import kotlin.test.assertEquals
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Testcontainers
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class PersonIntegrationTest {
//
//    companion object {
////        @JavaDispatcher.Container
//        val kafka = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.0"))
//
////        @JavaDispatcher.Container
//        val mongo = MongoDBContainer(DockerImageName.parse("mongo:7.0"))
//    }
//
//    @DynamicPropertySource
//    fun properties(registry: DynamicPropertyRegistry) {
//        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers)
//        registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl)
//    }
//
//    @Autowired
//    lateinit var webTestClient: WebTestClient
//
//    @Autowired
//    lateinit var repository: PersonRepository
//
//    @Autowired
//    lateinit var kafkaConsumerFactory: ConsumerFactory<String, String>
//
////    lateinit var consumer: KafkaProperties.Consumer<String, String>
//
////    @BeforeEach
////    fun setupKafkaConsumer() {
////        consumer = kafkaConsumerFactory.createConsumer()
////        val container = KafkaTestUtils.getContainer(consumer)
////        container.subscribe(listOf("people-topic"))
////    }
////
////    @AfterEach
////    fun tearDown() {
////        consumer.close()
////        repository.deleteAll()
////    }
////
//
//    @Test
//    fun `should start test`() {
//        var r = "ddd";
//    }
//
////    @Test
////    fun `should create person and produce kafka event`() {
////        val personJson = """{"name":"Alice","email":"alice@example.com"}"""
////
////        webTestClient.post()
////            .uri("/people")
////            .contentType(MediaType.APPLICATION_JSON)
////            .bodyValue(personJson)
////            .exchange()
////            .expectStatus().isCreated
////            .expectBody()
////            .jsonPath("$.id").isNotEmpty
////            .jsonPath("$.name").isEqualTo("Alice")
////            .jsonPath("$.email").isEqualTo("alice@example.com")
////
////        // Check MongoDB
////        val all = repository.findAll()
////        assertEquals(1, all.size)
////        assertEquals("Alice", all.first().name)
////
////        // Check Kafka
////        val record = KafkaTestUtils.getSingleRecord(consumer, "people-topic")
////        assertEquals("Alice", record.value())
////    }
//}
