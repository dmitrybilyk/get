//package com.knowledge.get.kotlin.kafka
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import org.springframework.kafka.core.KafkaTemplate
//import org.springframework.stereotype.Service
//
//@Service
//class ItemKafkaProducer(
//    private val kafkaTemplate: KafkaTemplate<String, String>,
//    private val objectMapper: ObjectMapper
//) {
//    fun send(topic: String, item: Any) {
//        val json = objectMapper.writeValueAsString(item)
//        println(json)
//        kafkaTemplate.send(topic, json)
//    }
//    fun sendList(topic: String, item: Any) {
//        val json = objectMapper.writeValueAsString(item)
//        println(json)
//        kafkaTemplate.send(topic, json)
//    }
//}
