package com.knowledge.get

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import

@SpringBootApplication
@ComponentScan(basePackages = [
	"com.proper.classes",
	"com.knowledge.get",
	"com.proper.classes.repository"])
@Import(RSocketClientConfig::class)
class GetApplication

fun main(args: Array<String>) {
	runApplication<GetApplication>(*args)
}
