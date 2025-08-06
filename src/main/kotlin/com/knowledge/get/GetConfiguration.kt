package com.knowledge.get

import com.fasterxml.jackson.core.type.TypeReference
import com.knowledge.get.kotlin.model.Item
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.util.backoff.FixedBackOff

@Configuration
class GetConfiguration(private val kafkaTemplate: KafkaTemplate<String, Any>) {

//    @Bean
//    fun rSocketRequester(builder: RSocketRequester.Builder): RSocketRequester {
//        return builder.tcp("localhost", 7000)
//    }

    @Bean
    fun itemKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Item> {
        val deserializer = JsonDeserializer(Item::class.java).apply {
            addTrustedPackages("*")
        }

        val errorHandlingDeserializer = ErrorHandlingDeserializer(deserializer)

        val props = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
            ConsumerConfig.GROUP_ID_CONFIG to "item-consumer-groups",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to ErrorHandlingDeserializer::class.java,
            ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS to JsonDeserializer::class.java.name
        )

        val consumerFactory = DefaultKafkaConsumerFactory<String, Item>(
            props,
            StringDeserializer(),
            errorHandlingDeserializer
        )

        return ConcurrentKafkaListenerContainerFactory<String, Item>().apply {
            this.consumerFactory = consumerFactory
//            this.setCommonErrorHandler(errorHandler())
        }
    }

    @Bean
    fun itemListKafkaListenerContainerFactory(errorHandler: DefaultErrorHandler): ConcurrentKafkaListenerContainerFactory<String, List<Item>> {
        val deserializer = JsonDeserializer<List<Item>>(object : TypeReference<List<Item>>() {}).apply {
            addTrustedPackages("*")
        }

        val errorHandlingDeserializer = ErrorHandlingDeserializer(deserializer)

        val props = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
            ConsumerConfig.GROUP_ID_CONFIG to "item-consumer-groups",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to ErrorHandlingDeserializer::class.java,
            ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS to JsonDeserializer::class.java.name
        )

        val consumerFactory = DefaultKafkaConsumerFactory<String, List<Item>>(
            props,
            StringDeserializer(),
            errorHandlingDeserializer
        )

        return ConcurrentKafkaListenerContainerFactory<String, List<Item>>().apply {
            this.consumerFactory = consumerFactory
            this.setCommonErrorHandler(errorHandler)
        }
    }

    @Bean
    fun errorHandler(): DefaultErrorHandler {
        // Retry 3 times with 1 second delay between retries
        val backOff = FixedBackOff(1000L, 3)

        val recoverer = DeadLetterPublishingRecoverer(kafkaTemplate) { record: ConsumerRecord<*, *>, ex: Exception ->
            // Send to topic-name.DLT
            TopicPartition("${record.topic()}.DLT", record.partition())
        }

        return DefaultErrorHandler(recoverer, backOff)
    }

}