package org.dnltsk.umbrellasuggester.openweathermap

import org.springframework.stereotype.Component

@Component
class UmbrellaSuggester {

    private val clearUmbrellaRecommendations = listOf(
        "Thunderstorm", "Drizzle", "Rain"
    )

    fun isUmbrellaSuggested(currentWeatherData: OwmData2p5Response): Boolean {
        return currentWeatherData.weather.any { clearUmbrellaRecommendations.contains(it.main) }
    }

}