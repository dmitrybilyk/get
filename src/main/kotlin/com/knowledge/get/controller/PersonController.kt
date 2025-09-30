package com.knowledge.get.controller

import com.knowledge.get.model.Person
import com.knowledge.get.service.PersonService
import com.knowledge.get.service.PersonWithAddresses
import com.knowledge.get.service.SlowPersonService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/people")
class PersonController(private val personService: PersonService,
                       private val slowPersonService: SlowPersonService) {

    @GetMapping
    @MaskFields(["email","name"])
    fun all(): Flux<Person> = personService.getAll()

    @GetMapping("/mono")
    fun allMono(): Mono<List<Person>> = personService.getAllMono()

    @GetMapping("/{id}")
    fun one(@PathVariable id: String): Mono<ResponseEntity<Person>> =
        personService.getById(id)
            .map { ResponseEntity.ok(it) }
            .defaultIfEmpty(ResponseEntity.notFound().build())

    @PostMapping
    fun create(@RequestBody person: Person): Mono<Person> = personService.create(person)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): Mono<ResponseEntity<Void>> =
        personService.delete(id)
            .thenReturn(ResponseEntity.noContent().build())

    @GetMapping("/slow/{id}")
    fun slow(@PathVariable id: String): Mono<Person> =
        slowPersonService.getEnriched(id)

    @GetMapping("/with-addresses/{id}")
    fun personWithAddress(@PathVariable id: String): Mono<PersonWithAddresses> =
        personService.getPersonWithAddresses(id)

}
