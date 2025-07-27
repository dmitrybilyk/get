package com.knowledge.get.banking.repository

import com.knowledge.get.banking.model.BankAccount
import com.knowledge.get.banking.model.dto.CustomerBalanceSummary
import com.knowledge.get.banking.model.dto.TopCustomerSummary
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface BankAccountRepository : ReactiveCrudRepository<BankAccount, String> {

    fun findByCustomerId(customerId: String): Flux<BankAccount>

    // Find accounts by customer ID and currency using @Query
    @Query("{ 'customerId': ?0, 'currency': ?1 }")
    fun findByCustomerIdAndCurrency(customerId: String, currency: String): Flux<BankAccount>

    // Advanced aggregation query
    @Aggregation(pipeline = [
        "{ \$match: { customerId: ?0, \$and: [ ?1 ] } }",
        "{ \$group: { _id: { customerId: '\$customerId', currency: '\$currency' }, totalBalance: { \$sum: '\$balance' } } }",
        "{ \$project: { _id: 0, customerId: '\$_id.customerId', currency: '\$_id.currency', totalBalance: 1 } }",
        "{ \$sort: { totalBalance: -1 } }"
    ])
    fun getTotalBalanceByCustomerAndOptionalCurrency(
        customerId: String, currencyFilter: Map<String, String>?): Flux<CustomerBalanceSummary>

    @Aggregation(pipeline = [
        "{ \$match: { \$and: [ ?0 ] } }",
        "{ \$group: { _id: { customerId: '\$customerId', currency: '\$currency' }, totalBalance: { \$sum: '\$balance' } } }",
        "{ \$sort: { totalBalance: -1 } }",
        "{ \$limit: ?1 }",
        "{ \$lookup: { from: 'customers', localField: '_id.customerId', foreignField: '_id', as: 'customerDetails' } }",
        "{ \$unwind: '\$customerDetails' }",
        "{ \$project: { _id: 0, customerId: '\$_id.customerId', currency: '\$_id.currency', totalBalance: 1, customerName: '\$customerDetails.name', customerEmail: '\$customerDetails.email' } }"
    ])
    fun getTopCustomersByBalance(currencyFilter: Map<String, Any>?, limit: Int): Flux<TopCustomerSummary>
}
