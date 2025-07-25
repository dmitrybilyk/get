//package com.knowledge.get.controller
//
//import com.knowledge.get.amqp.MyKafkaProducer
//import org.springframework.web.bind.annotation.*
//
//
//data class KafkaMessage(val topic: String, val message: String)
//
//@RestController
//@RequestMapping("/kafka")
//class KafkaController(private val producer: MyKafkaProducer) {
//
//    @PostMapping("/send")
//    fun send(@RequestParam message: String): String {
//        producer.send("my-topic", message)
//        return "Sent: $message"
//    }
//
//    @PostMapping("/send-json")
//    fun sendJson(@RequestBody request: KafkaMessage): String {
//        producer.send(request.topic, request.message)
//        return "Sent to ${request.topic}: ${request.message}"
//    }
//}
