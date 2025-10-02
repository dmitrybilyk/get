//package com.knowledge.get.kotlin.service
//
//import com.knowledge.get.kotlin.exception.NonPositivePriceException
//import com.knowledge.get.kotlin.kafka.ItemKafkaProducer
//import com.knowledge.get.kotlin.model.Item
//import com.knowledge.get.kotlin.repository.ItemRepository
//import org.assertj.core.api.Assertions.assertThat
//import org.bson.types.ObjectId
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.InjectMocks
//import org.mockito.Mock
//import org.mockito.junit.jupiter.MockitoExtension
//import org.mockito.kotlin.any
//import org.mockito.kotlin.eq
//import org.mockito.kotlin.never
//import org.mockito.kotlin.verify
//import org.mockito.kotlin.verifyNoInteractions
//import org.mockito.kotlin.whenever
//import org.springframework.data.mongodb.core.ReactiveMongoTemplate
//import reactor.core.publisher.Mono
//import reactor.test.StepVerifier
//
//@ExtendWith(MockitoExtension::class)
//class ItemServiceTest {
//
//    @Mock
//    lateinit var enrichItemService: EnrichItemService
//
//    @Mock
//    lateinit var itemRepository: ItemRepository
//
//    @Mock
//    lateinit var kafkaProducer: ItemKafkaProducer
//
//    @Mock
//    lateinit var mongoTemplate: ReactiveMongoTemplate
//
//    @Mock
//    lateinit var producerService: ProducerService
//
//    @InjectMocks
//    private lateinit var itemService: ItemService
//
//    @BeforeEach
//    fun setUp() {
//        itemService = ItemService(itemRepository, enrichItemService, mongoTemplate, producerService, kafkaProducer)
//    }
//
//    @Test
//    fun `should save item successfully and send to Kafka`() {
//        // given
//        val item = Item(name = "phone", price = 200.0, producerId = ObjectId())
//        val enriched = item.copy(name = "ENRICHED")
//
//        whenever(enrichItemService.enrichItem(any())).thenReturn(Mono.just(enriched))
//        whenever(itemRepository.save(enriched)).thenReturn(Mono.just(enriched))
//
//        // when & then
//        StepVerifier.create(itemService.save(item))
//            .expectSubscription()
//            .expectNext(enriched)
//            .verifyComplete()
//
//        // then: kafkaProducer should be called
//        verify(kafkaProducer).send(eq("item-topic"), eq(enriched))
//        verify(kafkaProducer).sendList(eq("items-topic"), eq(listOf(enriched)))
//    }
//
//    @Test
//    fun `should fail if price is not positive`() {
//        val item = Item(name = "cheap", price = 0.0, producerId = ObjectId())
//
//        StepVerifier.create(itemService.save(item))
//            .expectErrorSatisfies { error ->
//                assertThat(error).isInstanceOf(NonPositivePriceException::class.java)
//                assertThat(error.message).contains("Item price must be positive")
//            }
//            .verify()
//
//        verifyNoInteractions(enrichItemService, itemRepository, kafkaProducer)
//    }
//
//    @Test
//    fun `should fail if enrichment fails`() {
//        val item = Item(name = "fail", price = 200.0, producerId = ObjectId())
//
//        whenever(enrichItemService.enrichItem(any()))
//            .thenReturn(Mono.error(RuntimeException("External service error")))
//
//        StepVerifier.create(itemService.save(item))
//            .expectErrorSatisfies { error ->
//                assertThat(error).isInstanceOf(RuntimeException::class.java)
//                assertThat(error.message).contains("Failed to enrich item")
//            }
//            .verify()
//
//        verifyNoInteractions(itemRepository, kafkaProducer)
//    }
//
//    @Test
//    fun `should fail if persistence fails`() {
//        val item = Item(name = "fail", price = 200.0, producerId = ObjectId())
//        val enriched = item.copy(name = "OK")
//
//        whenever(enrichItemService.enrichItem(any())).thenReturn(Mono.just(enriched))
//        whenever(itemRepository.save(enriched)).thenReturn(Mono.error(RuntimeException("DB down")))
//
//        StepVerifier.create(itemService.save(item))
//            .expectErrorSatisfies { error ->
//                assertThat(error).isInstanceOf(RuntimeException::class.java)
//                assertThat(error.message).contains("DB down")
//            }
//            .verify()
//
//        verify(kafkaProducer, never()).send(any(), any())
//    }
//}
