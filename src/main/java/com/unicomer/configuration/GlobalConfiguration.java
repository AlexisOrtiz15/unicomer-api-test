package com.unicomer.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Global configuration.
 *
 */
@lombok.Generated
@Configuration
public class GlobalConfiguration {

	/**
	 * RestTemplate configuration.
	 *
	 * @param restTemplateBuilder the restTemplateBuilder
	 * @return the restTemplate
	 */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}

}
