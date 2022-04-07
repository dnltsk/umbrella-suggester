package org.dnltsk.umbrellasuggester.openweathermap

interface IOwmClient {

    fun receiveLocation(locationName: String): OwmGeo1p0DirectResponse

    fun receiveCurrentWeather(location: OwmGeo1p0DirectResponse.Location): OwmData2p5Response

}