package com.knowledge.get

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(RSocketClientConfig::class)
class GetApplication

fun main(args: Array<String>) {
	runApplication<GetApplication>(*args)
}
