package com.knowledge.get.kotlin.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.knowledge.get.kotlin.model.Item
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ItemKafkaConsumer(val objectMapper: ObjectMapper) {

    @KafkaListener(
        topics = ["item-topic"],
        groupId = "item-consumer-groups",
        containerFactory = "itemKafkaListenerContainerFactory")
    fun consume(item: Item) {
//        val item = objectMapper.readValue(message, Item::class.java)
        if (item.name == "fail") {
            throw RuntimeException("fail777777777777777")
        }
        println(">> Consumed item: $item")
    }

//    @KafkaListener(topics = ["items-topic"], groupId = "items-consumer-groups")
//    fun consumeList(message: String) {
//        val items: List<Item> = objectMapper.readValue(message, object : TypeReference<List<Item>>() {})
//        println(">> Consumed list of items: $items")
//    }

    @KafkaListener(
        topics = ["items-topic"],
        groupId = "items-consumer-groups",
        containerFactory = "itemListKafkaListenerContainerFactory")
    fun consumeList(items: List<Item>) {
        println(">> Consumed list of items directly: $items")
    }

    @KafkaListener(topics = ["test-partitioned"], groupId = "test-consumer-group")
    fun listen(record: ConsumerRecord<String, String>) {
        val key = record.key()
        val value = record.value()
        val partition = record.partition()

        println("📥 Received message: key=[$key], value=[$value], partition=[$partition]")
    }
}
