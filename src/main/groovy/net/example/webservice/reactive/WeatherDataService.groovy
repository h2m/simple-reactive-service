package net.example.webservice.reactive

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Slf4j
@Service
class WeatherDataService {

	private static final WEATHER_DATA_API_URl = 'http://api.met.no/weatherapi/locationforecast/1.9'

	private final webClient = WebClient.create WEATHER_DATA_API_URl

	Mono<String> retrieveWeatherData(String latitude, String longitude) {

		webClient.get().uri('?lat={lat}&lon={lon}', latitude, longitude).exchange()

			// .bodyToMono(String)
			.flatMap { ClientResponse response ->
				// see https://github.com/reactor/reactor-netty/issues/85
				// response.statusCode() -> NPE

				// DefaultClientResponse.ReactorClientHttpResponse.HttpClientOperations.status()
				if (response.response.response.status() == null) {
					log.error 'Status code is null'
				}

				response.bodyToMono(String)
	        }
	}
}
