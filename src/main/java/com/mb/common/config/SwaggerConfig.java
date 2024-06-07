package com.mb.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * Swagger(Open API) configuration
 * 
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 *
 */
@Configuration
public class SwaggerConfig {

	/**
	 * Configuration bean for setting documentation or version related information
	 * of API using OpenAPI.
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @return {@link OpenAPI}
	 */
	@Bean
	OpenAPI openApi() {

		Info info = new Info().title("Kuruhinshetty Samaj API").version("v1.0")
				.description("This API exposes endpoints to manage spring boot rest app.");

		return new OpenAPI().info(info);
	}

}
