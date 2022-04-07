package org.dnltsk.umbrellasuggester.openweathermap

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("mock")
class OwmClientMock : IOwmClient {

    override fun receiveLocation(locationName: String): OwmGeo1p0DirectResponse {
        return OwmGeo1p0DirectResponse().apply {
            add(
                OwmGeo1p0DirectResponse.Location(
                    name = "Berlin",
                    lat = 52.518611,
                    lon = 13.408333,
                )
            )
        }
    }

    override fun receiveCurrentWeather(location: OwmGeo1p0DirectResponse.Location): OwmData2p5Response {
        return OwmData2p5Response(
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
    }

}