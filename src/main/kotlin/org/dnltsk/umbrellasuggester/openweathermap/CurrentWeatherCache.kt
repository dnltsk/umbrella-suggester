package org.dnltsk.umbrellasuggester.openweathermap

import org.dnltsk.umbrellasuggester.WeatherSituation
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class CurrentWeatherCache {

    private val cache = ConcurrentHashMap<String, ArrayList<WeatherSituation>>()

    fun addWeatherSituation(locationName: String, currentWeather: WeatherSituation) {
        val cachedSituations = cache.computeIfAbsent(locationName) { arrayListOf() }
        cachedSituations.add(currentWeather)
        if(cachedSituations.size > 5) {
            cachedSituations.removeAt(0)
        }
    }

    fun getHistory(locationName: String): ArrayList<WeatherSituation> {
        return cache.getOrDefault(locationName, arrayListOf())
    }

}