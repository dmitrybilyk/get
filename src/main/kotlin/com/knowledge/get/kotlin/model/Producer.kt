package com.knowledge.get.kotlin.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank

@Document("producers")
public data class Producer(
    @Id val id: String? = null,
    @field:NotBlank val name: String,
    @field:NotBlank val country: String,
    val address: String? = null
)
