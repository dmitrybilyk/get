package com.knowledge.get.patterns.structural

fun main() {
    val city = City(WeatherForecastAdapter(ModernWeatherForecastImpl()))
    city.announceWeather()

    val modernCity = ModernCity(ModernWeatherForecastAdapter(WeatherForecastImpl()))
    modernCity.announceModernWeather()
}


class City(val weatherForecastAdapter: WeatherForecastAdapter) {
    fun announceWeather() {
//        val forecast = weatherForecast.getForecast().plus("44444")
        val forecast = weatherForecastAdapter.getForecast().plus("44444")
        println(forecast)
    }
}

class ModernCity(val modernWeatherForecastAdapter: ModernWeatherForecastAdapter) {
    fun announceModernWeather() {
        val result = 55 + modernWeatherForecastAdapter.getModernForecast()
        println(result)
    }
}

interface WeatherForecast {
    fun getForecast(): String
}

class WeatherForecastImpl: WeatherForecast {
    override fun getForecast(): String {
        return "12334355"
    }
}

class WeatherForecastAdapter(val moderWeatherForecast: ModernWeatherForecast): WeatherForecast {
    override fun getForecast(): String {
        return moderWeatherForecast.getModernForecast().toString()
    }
}

class ModernWeatherForecastAdapter(val weatherForecast: WeatherForecast): ModernWeatherForecast {
    override fun getModernForecast(): Int {
        return weatherForecast.getForecast().toInt()
    }

}

interface ModernWeatherForecast {
    fun getModernForecast(): Int
}

class ModernWeatherForecastImpl: ModernWeatherForecast {
    override fun getModernForecast(): Int {
        return 5555555
    }

}