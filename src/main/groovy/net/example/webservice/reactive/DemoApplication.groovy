package net.example.webservice.reactive

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class DemoApplication {

	static void main(String[] args) {
		SpringApplication.run DemoApplication, args
	}
}
