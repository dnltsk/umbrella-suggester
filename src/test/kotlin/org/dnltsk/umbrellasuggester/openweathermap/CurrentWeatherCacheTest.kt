package org.dnltsk.umbrellasuggester.openweathermap

import org.assertj.core.api.Assertions.assertThat
import org.dnltsk.umbrellasuggester.WeatherSituation
import org.junit.jupiter.api.Test


class CurrentWeatherCacheTest {

    private val underTest = CurrentWeatherCache()

    @Test
    fun `last 5 weather situations are stored on cache`() {
        // given
        val cityName = "Berlin"
        val ws1 = WeatherSituation(temp = 1.0, pressure = 100.0, umbrella = true)
        val ws2 = WeatherSituation(temp = 2.0, pressure = 200.0, umbrella = false)
        val ws3 = WeatherSituation(temp = 3.0, pressure = 300.0, umbrella = true)
        val ws4 = WeatherSituation(temp = 4.0, pressure = 400.0, umbrella = false)
        val ws5 = WeatherSituation(temp = 5.0, pressure = 500.0, umbrella = true)
        val ws6 = WeatherSituation(temp = 6.0, pressure = 600.0, umbrella = false)

        //when
        underTest.addWeatherSituation(cityName, ws1)  // <- this gets removed :)
        underTest.addWeatherSituation(cityName, ws2)
        underTest.addWeatherSituation(cityName, ws3)
        underTest.addWeatherSituation(cityName, ws4)
        underTest.addWeatherSituation(cityName, ws5)
        underTest.addWeatherSituation(cityName, ws6)

        //then
        val history = underTest.getHistory(cityName)
        assertThat(history).isEqualTo(listOf(ws2, ws3, ws4, ws5, ws6))
    }

    @Test
    fun `cache returns empty list of city was never requested before`() {
        // given
        val cityName = "Berlin"

        //when
        val history = underTest.getHistory(cityName)

        //then
        assertThat(history).isEmpty()
    }


}
