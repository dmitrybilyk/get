package com.knowledge.get.patterns.creational.factory.simplefactory

sealed class Country {
    object Canada: Country()
}

object Spain: Country()
class Greece(val someProperty: String): Country()
data class USA(val someProperty: String): Country()

data class Currency (val code: String)

object CurrencyFactory {
    fun currencyForCountry(country: Country): Currency =
        when(country) {
            is Spain -> Currency("EUR")
            is Greece -> Currency("EUR")
            is USA -> Currency("USD")
            is Country.Canada -> Currency("CAD")
        }
}

fun main() {
    val country = Spain
    val currency = CurrencyFactory.currencyForCountry(country)
    println(currency)
}