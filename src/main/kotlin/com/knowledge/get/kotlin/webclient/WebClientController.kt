package com.knowledge.get.kotlin.webclient

import com.knowledge.get.kotlin.model.Item
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/webclient")
class WebClientController(private val itemClient: ItemClient) {

    @GetMapping("/items/{id}")
    fun triggerGetItem(@PathVariable id: String) {
        itemClient.getItemById(id)
            .subscribe { println(it) }
    }

    @PostMapping("/items")
    fun triggerCreateItem(@RequestBody item: Item) {
        itemClient.createItem(item).subscribe { println(it) }
    }
}