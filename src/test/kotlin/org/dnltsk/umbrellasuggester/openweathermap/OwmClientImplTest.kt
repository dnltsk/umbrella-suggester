package org.dnltsk.umbrellasuggester.openweathermap

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.dnltsk.umbrellasuggester.sampleLocationName
import org.dnltsk.umbrellasuggester.sampleOwmData2p5Response
import org.dnltsk.umbrellasuggester.sampleOwmGeo1p0DirectResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestOperations

class OwmClientImplTest {

    @InjectMocks
    private lateinit var underTest: OwmClientImpl

    @Mock
    private lateinit var restOperation: RestOperations

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        ReflectionTestUtils.setField(underTest, "openweathermapHost", "dummy-host")
        ReflectionTestUtils.setField(underTest, "openweathermapApiKey", "dummy-api-key")
    }

    @Test
    fun `receiveLocation() uses correct request`() {
        //given
        val expectedUrl = "https://dummy-host/geo/1.0/direct?q={locationName}&limit=1&appid={apiKey}"
        val expectedParams = mapOf(
            "locationName" to "Berlin",
            "apiKey" to "dummy-api-key",
        )
        whenever(
            restOperation.getForObject(
                eq(expectedUrl),
                eq(OwmGeo1p0DirectResponse::class.java),
                eq(expectedParams),
            )
        ).thenReturn(sampleOwmGeo1p0DirectResponse)

        //when
        val result = underTest.receiveLocation(sampleLocationName)

        //then
        assertThat(result).isEqualTo(sampleOwmGeo1p0DirectResponse)
    }

    @Test
    fun `receiveLocation() forwards exception`() {
        //given
        whenever(
            restOperation.getForObject(any(), eq(OwmGeo1p0DirectResponse::class.java), any())
        ).thenThrow(RestClientException("someting went wrong"))

        //when & then
        assertThatThrownBy {
            underTest.receiveLocation(sampleLocationName)
        }.isInstanceOf(RuntimeException::class.java)
            .hasMessage("Cannot load location")
    }

    @Test
    fun `receiveCurrentWeather() uses correct request`() {
        //given
        val expectedUrl = "https://dummy-host/data/2.5/weather?lat={lat}&lon={lon}&appid={apiKey}&units=Metric"
        val expectedParams = mapOf(
            "lat" to "52.518611",
            "lon" to "13.408333",
            "apiKey" to "dummy-api-key",
        )
        whenever(
            restOperation.getForObject(
                eq(expectedUrl),
                eq(OwmData2p5Response::class.java),
                eq(expectedParams),
            )
        ).thenReturn(sampleOwmData2p5Response)

        //when
        val result = underTest.receiveCurrentWeather(sampleOwmGeo1p0DirectResponse[0])

        //then
        assertThat(result).isEqualTo(sampleOwmData2p5Response)
    }

    @Test
    fun `receiveCurrentWeatherData() forwards exception`() {
        //given
        whenever(
            restOperation.getForObject(any(), eq(OwmData2p5Response::class.java), any())
        ).thenThrow(RestClientException("someting went wrong"))

        //when & then
        assertThatThrownBy {
            underTest.receiveCurrentWeather(sampleOwmGeo1p0DirectResponse[0])
        }.isInstanceOf(RuntimeException::class.java)
            .hasMessage("Cannot load weather data")
    }

}