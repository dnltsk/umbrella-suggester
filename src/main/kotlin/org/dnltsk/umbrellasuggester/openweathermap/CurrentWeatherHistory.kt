package org.dnltsk.umbrellasuggester.openweathermap

import org.dnltsk.umbrellasuggester.WeatherSituation
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class CurrentWeatherHistory {

    private val maxHistoryLength = 5

    private val history = ConcurrentHashMap<String, ArrayList<WeatherSituation>>()

    fun addWeatherSituation(locationName: String, currentWeather: WeatherSituation) {
        val currentHistory = history.computeIfAbsent(locationName) { arrayListOf() }
        currentHistory.add(currentWeather)
        if(currentHistory.size > maxHistoryLength) {
            currentHistory.removeAt(0)
        }
    }

    fun getHistory(locationName: String): ArrayList<WeatherSituation> {
        return history.getOrDefault(locationName, arrayListOf())
    }

}