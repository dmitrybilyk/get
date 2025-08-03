package com.knowledge.get.kotlin.service

import com.knowledge.get.kotlin.model.Producer
import com.knowledge.get.kotlin.repository.ProducerRepository
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ProducerService(private val producerRepository: ProducerRepository) {

    fun save(producer: Producer): Mono<Producer> {
        return producerRepository.save(producer)
    }

    fun getProducerById(id: String): Mono<Producer> = producerRepository.findById(id)

    fun getProducers(page: Int, size: Int): Mono<PageImpl<Producer>> {
        val pageable = PageRequest.of(page, size)

        val itemsMono = producerRepository.findAllBy(pageable).collectList()
        val countMono = producerRepository.count()

        return itemsMono.zipWith(countMono)
            .map { tuple ->
                PageImpl(tuple.t1, pageable, tuple.t2)
            }
    }

    fun updateProducer(id: String, producer: Producer): Mono<Producer> {
        return producerRepository.findById(id)
            .switchIfEmpty(Mono.error(IllegalArgumentException("Producer not found with id $id")))
            .flatMap {
                producerRepository.save(producer)
            }
    }

    fun deleteById(id: String): Mono<Void> {
        return producerRepository.deleteById(id)
    }
}