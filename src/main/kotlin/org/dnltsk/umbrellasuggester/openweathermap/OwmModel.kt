package org.dnltsk.umbrellasuggester.openweathermap

data class OwmData2p5Response(
    val main: Main,
    val weather: List<Weather>,
) {

    data class Main(
        val pressure: Double,
        val temp: Double,
    )

    data class Weather(
        val main: String
    )

}

class OwmGeo1p0DirectResponse : ArrayList<OwmGeo1p0DirectResponse.Location>() {
    data class Location(
        val name: String,
        /**
         * latitude, aka Y
         */
        val lat: Double,
        /**
         * longitude, aka X
         */
        val lon: Double,
    )
}
