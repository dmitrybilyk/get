package com.knowledge.get.kotlin.controller

import com.knowledge.get.kotlin.controller.response.ApiResponse
import com.knowledge.get.kotlin.model.Producer
import com.knowledge.get.kotlin.service.ProducerService
import org.springframework.data.domain.PageImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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

    @GetMapping("/{id}")
    fun getProducerById(@PathVariable id: String): Mono<ResponseEntity<ApiResponse<Producer>>> {
        return producerService.getProducerById(id)
            .map { ResponseEntity.ok(ApiResponse.Success(it)) }
    }

    @GetMapping
    fun getProducers(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Mono<ResponseEntity<ApiResponse<PageImpl<Producer>>>> {
        return producerService.getProducers(page, size)
            .map { producers -> ResponseEntity.ok(ApiResponse.Success(producers)) }
    }

    @PutMapping("/{id}")
    fun updateProducer(@PathVariable id: String, @RequestBody producer: Producer): Mono<ResponseEntity<ApiResponse<Producer>>> {
        return producerService.updateProducer(id, producer)
            .map { ResponseEntity.ok(ApiResponse.Success(it)) }
    }

    @DeleteMapping
    fun deleteProducer(@RequestParam id: String): Mono<ResponseEntity<ApiResponse<Void>>> {
        return producerService.deleteById(id)
            .thenReturn(ResponseEntity.noContent().build())
    }
}