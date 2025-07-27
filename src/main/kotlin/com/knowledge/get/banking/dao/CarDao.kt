package com.knowledge.get.banking.dao

import com.knowledge.get.banking.Car
import com.knowledge.get.banking.Person
import java.lang.Thread.sleep

class CarDao {
    fun getCars(): List<Car> {
        sleep(1000)
        println("Fetching cars in dao, thread = " + Thread.currentThread().name)
        return listOf(
            Car("car1", "person1"),
            Car("car2", "person2"),
            Car("car3", "person3"))
    }
}