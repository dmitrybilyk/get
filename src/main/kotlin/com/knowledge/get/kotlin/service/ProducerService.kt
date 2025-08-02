package com.knowledge.get.kotlin.service

import com.knowledge.get.kotlin.model.Producer
import com.knowledge.get.kotlin.repository.ProducerRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ProducerService(private val producerRepository: ProducerRepository) {
    fun getProducerById(id: String): Mono<Producer> = producerRepository.findById(id)

    fun save(producer: Producer): Mono<Producer> {
        return producerRepository.save(producer)
    }
}