package org.dnltsk.umbrellasuggester.openweathermap

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.client.RestOperations

@Service
@Profile("!mock")
class OwmClientImpl : IOwmClient {

    @Autowired
    private lateinit var restOperation: RestOperations

    @Value("\${openweathermap.host}")
    private lateinit var openweathermapHost: String

    @Value("\${openweathermap.apiKey}")
    private lateinit var openweathermapApiKey: String

    override fun receiveLocation(locationName: String): OwmGeo1p0DirectResponse {
        try {
            val url = "https://$openweathermapHost/geo/1.0/direct?q={locationName}&limit=1&appid={apiKey}"
            val params = mapOf(
                "locationName" to locationName,
                "apiKey" to openweathermapApiKey,
            )
            return restOperation.getForObject(url, OwmGeo1p0DirectResponse::class.java, params)!!
        } catch (e: Exception) {
            throw RuntimeException("Cannot load location", e)
        }
    }

    override fun receiveCurrentWeather(location: OwmGeo1p0DirectResponse.Location): OwmData2p5Response {
        try {
            val url = "https://$openweathermapHost/data/2.5/weather?lat={lat}&lon={lon}&appid={apiKey}&units=Metric"
            val params = mapOf(
                "lat" to "${location.lat}",
                "lon" to "${location.lon}",
                "apiKey" to openweathermapApiKey,
            )
            return restOperation.getForObject(url, OwmData2p5Response::class.java, params)!!
        } catch (e: Exception) {
            throw RuntimeException("Cannot load weather data", e)
        }
    }

}