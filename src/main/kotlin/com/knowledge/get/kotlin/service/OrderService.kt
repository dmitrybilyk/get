package com.knowledge.get.kotlin.service

import com.knowledge.get.kotlin.model.Order
import com.knowledge.get.kotlin.model.OrderWithUser
import com.knowledge.get.kotlin.model.User
import com.knowledge.get.kotlin.repository.OrderRepository
import com.knowledge.get.kotlin.repository.UserRepository
import org.bson.Document
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.aggregation.AggregationResults
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository,
    private val mongoTemplate: ReactiveMongoTemplate
) {

    fun getAllOrders(): Flux<Order> = orderRepository.findAll()

    fun getOrderWithUsers(): Flux<OrderWithUser> {
        val aggregation = newAggregation(
            lookup("users", "userId", "_id", "user"),
            unwind("user")
        )

        return mongoTemplate.aggregate(aggregation, "orders", Document::class.java)
            .map {
                OrderWithUser(
                    id = it.getString("_id"),
                    userId = it.getString("userId"),
                    total = it.getDouble("total"),
                    items = it.getList("items", String::class.java),
                    user = User(
                        id = (it["user"] as Document).getString("_id"),
                        name = (it["user"] as Document).getString("name"),
                        email = (it["user"] as Document).getString("email")
                    )
                )
            }
    }

    fun createOrder(order: Order): Mono<Order> = orderRepository.save(order)

    fun createUser(user: User): Mono<User> = userRepository.save(user)

    fun getOrdersPaginated(page: Int, size: Int): Mono<PageImpl<Order>> {
        val pageable: Pageable = PageRequest.of(page, size)

        val ordersFlux = orderRepository.findAll()
            .skip(pageable.offset)
            .take(pageable.pageSize.toLong())

        val countMono = orderRepository.count()

        return ordersFlux.collectList()
            .zipWith(countMono) { orders, count ->
                PageImpl(orders, pageable, count)
            }
    }
}