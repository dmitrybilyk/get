package com.knowledge.get.kotlin.controller

import com.knowledge.get.kotlin.model.Order
import com.knowledge.get.kotlin.model.OrderWithUser
import com.knowledge.get.kotlin.model.User
import com.knowledge.get.kotlin.service.OrderService
import org.springframework.data.domain.PageImpl
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api")
class OrderController(
    private val orderService: OrderService
) {

    @PostMapping("/users")
    fun createUser(@RequestBody user: User): Mono<User> = orderService.createUser(user)

    @PostMapping("/orders")
    fun createOrder(@RequestBody order: Order): Mono<Order> = orderService.createOrder(order)

    @GetMapping("/orders")
    fun getOrders(): Flux<Order> = orderService.getAllOrders()

    @GetMapping("/orders/paginated")
    fun getOrders(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Mono<PageImpl<Order>> = orderService.getOrdersPaginated(page, size)

    @GetMapping("/orders-with-user")
    fun getOrdersWithUsers(): Flux<OrderWithUser> = orderService.getOrderWithUsers()
}