package com.knowledge.get.kotlin.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.knowledge.get.kotlin.model.Item
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ItemKafkaConsumer(val objectMapper: ObjectMapper) {

    @KafkaListener(topics = ["item-topic"], groupId = "item-consumer-groups")
    fun consume(message: String) {
        val item = objectMapper.readValue(message, Item::class.java)
        println(">> Consumed item: $item")
    }
}
