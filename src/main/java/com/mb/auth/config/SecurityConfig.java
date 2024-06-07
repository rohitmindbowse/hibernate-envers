package com.mb.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

/**
 * @author Mindbowser | rohit.kumar@mindbowser.com
 *
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Resource(name = "customUserDetailsService")
	private UserDetailsService userDetailsService;

	private final JwtRequestFilter jwtRequestFilter;

	/**
	 * BCryptPassword encoder bean for encrypting the password
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @return {@link PasswordEncoder}
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * returns authentication manager bean for authenticating the user
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param authenticationConfiguration
	 * @return {@link AuthenticationManager}a
	 * @throws Exception
	 */
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	/**
	 * returns authentication provider bean with userdetails service and password
	 * encoder
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @return {@link DaoAuthenticationProvider}
	 */
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	/**
	 * Security filter chain
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param http
	 * @return {@link SecurityFilterChain}
	 * @throws Exception
	 */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth.requestMatchers("/**").permitAll().anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
				.authenticationProvider(authenticationProvider()).build();
	}

}
