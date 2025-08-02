package com.knowledge.get.kotlin.controller

import com.knowledge.get.kotlin.controller.response.ApiResponse
import com.knowledge.get.kotlin.model.Producer
import com.knowledge.get.kotlin.service.ProducerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/producers")
class ProducerController(private val producerService: ProducerService) {


    @PostMapping
    fun createProducer(@Valid @RequestBody producer: Producer): Mono<ResponseEntity<ApiResponse<Producer>>> {
        return producerService.save(producer)
            .map { savedProducer ->
                ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.Success(savedProducer))
            }
    }
}