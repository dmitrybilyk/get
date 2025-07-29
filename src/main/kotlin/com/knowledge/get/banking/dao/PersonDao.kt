package com.knowledge.get.banking.dao

import com.knowledge.get.banking.Person
import java.lang.Thread.sleep
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PersonDao {
    fun getPersons(): List<Person> {
        sleep(1000)
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")
        println(now.format(formatter))
        println("Fetching persons in dao, thread = " + Thread.currentThread().name)
        return listOf(
            Person("person1", "Lviv"),
            Person("person2", "Lviv"),
            Person("person3", "Lviv"))
    }
}