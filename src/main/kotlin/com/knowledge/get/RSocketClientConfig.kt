package com.knowledge.get

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler
import reactor.core.publisher.Mono

@Configuration
class RSocketClientConfig {

    @Bean
    fun rSocketRequester(rSocketStrategies: RSocketStrategies): Mono<RSocketRequester> {
        return RSocketRequester.builder()
            .rsocketStrategies(rSocketStrategies)
            .connectTcp("localhost", 7000)
    }

    @Bean
    fun rSocketStrategies(objectMapper: ObjectMapper): RSocketStrategies {
        return RSocketStrategies.builder()
            .encoder(Jackson2JsonEncoder(objectMapper))
            .decoder(Jackson2JsonDecoder(objectMapper))
            .build()
    }

    @Bean
    fun messageHandler(rSocketStrategies: RSocketStrategies): RSocketMessageHandler {
        val handler = RSocketMessageHandler()
        handler.rSocketStrategies = rSocketStrategies
        return handler
    }

    @Bean
    fun objectMapper(): ObjectMapper = jacksonObjectMapper()
}
