package com.knowledge.get.patterns

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class CalculatorTest {

    @Test
    fun testSum() {
        val calculator = Calculator()
        Assertions.assertThat(calculator.sum(3, 5) == 8)
    }
}