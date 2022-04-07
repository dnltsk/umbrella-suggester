package org.dnltsk.umbrellasuggester

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class HttpControllerTest {

    @InjectMocks
    private lateinit var httpController: HttpController

    @Mock
    private lateinit var requestHandler: RequestHandler

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `getCurrent forwards the locationName to RequestHandler`() {
        //when
        httpController.getCurrent(sampleLocationName)
        //then
        verify(requestHandler).loadWeatherSituation(sampleLocationName)
    }

    @Test
    fun `getCurrent returns WeatherSituation from RequestHandler`() {
        //given
        val weatherSituation = WeatherSituation(temp = 6.0, pressure = 600.0, umbrella = false)
        whenever(requestHandler.loadWeatherSituation(any())).thenReturn(weatherSituation)

        //when
        val response = httpController.getCurrent(sampleLocationName)

        //then
        assertThat(response.body).isEqualTo(weatherSituation)
    }

    @Test
    fun `getHistory forwards the cityName to RequestHandler`() {
        //when
        httpController.getHistory(sampleLocationName)
        //then
        verify(requestHandler).loadWeatherHistory(sampleLocationName)
    }

    @Test
    fun `getHistory returns WeatherHistory from RequestHandler`() {
        //given
        val weatherHistory = WeatherHistory(
            avgTemp = 6.0,
            avgPressure = 600.0,
            history = listOf(WeatherSituation(temp = 6.0, pressure = 600.0, umbrella = false))
        )
        whenever(requestHandler.loadWeatherHistory(any())).thenReturn(weatherHistory)

        //when
        val response = httpController.getHistory(sampleLocationName)

        //then
        assertThat(response.body).isEqualTo(weatherHistory)
    }

}