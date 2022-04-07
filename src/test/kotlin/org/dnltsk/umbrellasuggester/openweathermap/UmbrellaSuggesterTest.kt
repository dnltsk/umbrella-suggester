package org.dnltsk.umbrellasuggester.openweathermap

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UmbrellaSuggesterTest {

    private val underTest = UmbrellaSuggester()

    @ParameterizedTest
    @ValueSource(strings = ["Thunderstorm", "Drizzle", "Rain"])
    fun `umbrella is suggested on known criteria`(main: String) {
        //given
        val weatherSituation = OwmData2p5Response(
            main = OwmData2p5Response.Main(
                temp = 7.0,
                pressure = 1234.0
            ),
            weather = listOf(
                OwmData2p5Response.Weather(main = main),
            ),
        )

        //when
        val result = underTest.isUmbrellaSuggested(weatherSituation)

        //then
        assertThat(result).isTrue
    }

    @Test
    fun `umbrella is not suggested on unknown criteria`() {
        //given
        val weatherSituation = OwmData2p5Response(
            main = OwmData2p5Response.Main(
                temp = 7.0,
                pressure = 1234.0
            ),
            weather = listOf(
                OwmData2p5Response.Weather(main = "Sun"),
            ),
        )

        //when
        val result = underTest.isUmbrellaSuggested(weatherSituation)

        //then
        assertThat(result).isFalse
    }

    @Test
    fun `one single umbrella criteria is enough to suggest an umbrella`() {
        //given
        val weatherSituation = OwmData2p5Response(
            main = OwmData2p5Response.Main(
                temp = 7.0,
                pressure = 1234.0
            ),
            weather = listOf(
                OwmData2p5Response.Weather(main = "Sun"),
                OwmData2p5Response.Weather(main = "Cloud"),
                OwmData2p5Response.Weather(main = "Rain"), // <- umbrella alert :)
                OwmData2p5Response.Weather(main = "Fog"),
            ),
        )

        //when
        val result = underTest.isUmbrellaSuggested(weatherSituation)

        //then
        assertThat(result).isTrue
    }

}