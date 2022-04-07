package org.dnltsk.umbrellasuggester

import com.fasterxml.jackson.annotation.JsonProperty

data class WeatherSituation(
    val temp: Double,
    val pressure: Double,
    val umbrella: Boolean
)

data class WeatherHistory(
    @JsonProperty("avg_temp")
    val avgTemp: Double,
    @JsonProperty("avg_pressure")
    val avgPressure: Double,
    val history: List<WeatherSituation>
)