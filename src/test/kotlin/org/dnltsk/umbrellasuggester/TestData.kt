package org.dnltsk.umbrellasuggester

import org.dnltsk.umbrellasuggester.openweathermap.OwmData2p5Response
import org.dnltsk.umbrellasuggester.openweathermap.OwmGeo1p0DirectResponse

val sampleLocationName = "Berlin"

val sampleOwmData2p5Response = OwmData2p5Response(
    main = OwmData2p5Response.Main(
        pressure = 1234.0,
        temp = 7.0
    ),
    weather = listOf(
        OwmData2p5Response.Weather(
            main = "Rain",
        )
    )
)

val sampleOwmGeo1p0DirectResponse = OwmGeo1p0DirectResponse().apply {
    add(
        OwmGeo1p0DirectResponse.Location(
            name = "Berlin",
            lat = 52.518611,
            lon = 13.408333,
        )
    )
}