package org.dnltsk.umbrellasuggester

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HttpController {

    @Autowired
    private lateinit var requestHandler: RequestHandler

    @GetMapping("/current")
    fun getCurrent(@RequestParam(name = "location") locationName: String) : ResponseEntity<WeatherSituation> {
        val weatherSituation = requestHandler.loadWeatherSituation(locationName.lowercase())
        return ResponseEntity.ok(weatherSituation)
    }

    @GetMapping("/history")
    fun getHistory(@RequestParam(name = "location") locationName: String) : ResponseEntity<WeatherHistory> {
        val weatherHistory = requestHandler.loadWeatherHistory(locationName.lowercase())
        return ResponseEntity.ok(weatherHistory)
    }

}