package com.assessment.techieplanet;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"com.assessment.techieplanet"})
@SpringBootApplication(scanBasePackages = {"com.assessment.techieplanet"})
@OpenAPIDefinition(
		info = @Info(
			contact = @Contact(
				name = "Tolulope Ayemobola - TechiePlanet Documentation",
				email = "developer@techieplanetng.com"
			),
			description = "OpenApi Documentation for Tolu's TechiePlanet Assessment Submission",
			title = "Implementation API Documentation",
			version = "1.0",
			license = @License(
				name = "Named-by-TechiePlanet License",
				url = "https://techieplanetng.com"
			),
			termsOfService = "Terms of services"
		),
		security = {
				@SecurityRequirement(
						name = "bearerAuth"
				)
		}
)
@SecurityScheme(
		name = "bearerAuth",
		description = "JWT auth description",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER
)
public class TechiePlanetProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechiePlanetProjectApplication.class, args);
	}
}