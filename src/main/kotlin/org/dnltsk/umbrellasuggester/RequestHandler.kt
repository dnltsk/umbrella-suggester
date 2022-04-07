package org.dnltsk.umbrellasuggester

import org.dnltsk.umbrellasuggester.openweathermap.CurrentWeatherCache
import org.dnltsk.umbrellasuggester.openweathermap.IOwmClient
import org.dnltsk.umbrellasuggester.openweathermap.UmbrellaSuggester
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RequestHandler {

    @Autowired
    private lateinit var owmClient: IOwmClient

    @Autowired
    private lateinit var umbrellaSuggester: UmbrellaSuggester

    @Autowired
    private lateinit var cache: CurrentWeatherCache

    fun loadWeatherSituation(locationName: String): WeatherSituation {
        val geocodeResponse = owmClient.receiveLocation(locationName)

        val currentWeatherData = owmClient.receiveCurrentWeather(geocodeResponse[0])

        val result = currentWeatherData.run {
            WeatherSituation(
                temp = main.temp,
                pressure = main.pressure,
                umbrella = umbrellaSuggester.isUmbrellaSuggested(currentWeatherData),
            )
        }

        cache.addWeatherSituation(locationName, result)

        return result
    }

    fun loadWeatherHistory(locationName: String): WeatherHistory {
        val history = cache.getHistory(locationName)
        if (history.isEmpty()) {
            return loadSituationToFillCache(locationName)
        }
        return WeatherHistory(
            avgTemp = history.map { it.temp }.average(),
            avgPressure = history.map { it.pressure }.average(),
            history = history
        )
    }

    private fun loadSituationToFillCache(locationName: String): WeatherHistory {
        this.loadWeatherSituation(locationName)
        return this.loadWeatherHistory(locationName)
    }

}