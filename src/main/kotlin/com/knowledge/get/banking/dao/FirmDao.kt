package com.knowledge.get.banking.dao

import com.knowledge.get.banking.Firm
import com.knowledge.get.banking.Person
import java.lang.Thread.sleep

class FirmDao {
    fun getFirms(): List<Firm> {
        sleep(1000)
        println("Fetching firms in dao, thread = " + Thread.currentThread().name)
        return listOf(
            Firm("firm1", "person1"),
            Firm("firm2", "person2"),
            Firm("firm3", "person3"))
    }
}