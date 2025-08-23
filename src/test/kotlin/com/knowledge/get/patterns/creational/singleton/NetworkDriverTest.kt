package com.knowledge.get.patterns.creational.singleton

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class NetworkDriverTest{

    @Test
    fun testSingleton() {
        println("Start")
        val networkDriver = NetworkDriver.log()
        val networkDriver2 = NetworkDriver.log()

        Assertions.assertThat(networkDriver).isSameAs(NetworkDriver)
        Assertions.assertThat(networkDriver2).isSameAs(NetworkDriver)

    }
}