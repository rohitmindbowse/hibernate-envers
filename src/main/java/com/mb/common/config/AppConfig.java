package com.mb.common.config;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Add one or more beans for configuration
 * 
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 */
@Configuration
public class AppConfig {

	/**
	 * Model mapper bean used to perform object mapping
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @return {@link ModelMapper}
	 */
	@Bean
	ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		return modelMapper;
	}

	/**
	 * Global cors configuration
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @return {@link CorsConfigurationSource}
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowCredentials(true);

		List<String> strings = Arrays.asList("Authorization", "Requestor-Type", "Origin", "Content-Type", "Version");
		configuration.setAllowedHeaders(strings);
		configuration.setExposedHeaders(strings);
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
