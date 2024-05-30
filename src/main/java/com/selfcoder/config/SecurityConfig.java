package com.selfcoder.config;


import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {
	
	@Value("${publicKey}")
	private String publicKey;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable).cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
						.requestMatchers("/api/v1/users/signup", "/api/v1/users/login","/api/v1/users/addRole")

						.permitAll().anyRequest().authenticated())
				.oauth2ResourceServer(
						oauth2ResourceServer -> oauth2ResourceServer.jwt(jwt -> jwt.decoder(jwtDecoder())));

		return http.build();
	}

	
	@Bean
	JwtDecoder jwtDecoder() {
		try {
			// Replace this with your actual public key in Base64 format
			byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
			var keyFactory = KeyFactory.getInstance("RSA");
			var publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
			var rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
			return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();

		} catch (Exception e) {
			log.info("error msg " + e.getMessage());
			throw new IllegalStateException("Failed to configure JwtDecoder", e);

		}

	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		// Customize additional CORS settings as needed
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}
}