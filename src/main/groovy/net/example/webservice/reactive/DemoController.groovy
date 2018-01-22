package net.example.webservice.reactive

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@Slf4j
@RestController
class DemoController {

	@Autowired
	private WeatherDataService weatherDataService

	@GetMapping('/weather')
	Mono<String> getWeatherData(
		@RequestParam(value = 'lat', required = true) String latitude,
		@RequestParam(value = 'lon', required = true) String longitude) {

		weatherDataService.retrieveWeatherData(latitude, longitude)
			.doOnSuccess { weatherData ->
				log.info 'got weather data for location [{}, {}] ({})', latitude, longitude, "${(int) (weatherData.size() / 1024)} kB"
			}
	}
}
