package com.unicomer.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	/**
	 * Setting default ISO date converter. LocalDateTime Format:
	 * 'yyyy-MM-dd'T'HH:mm:ss.SSSZ'. LocalDate Format: 'yyyy-MM-dd'.
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
		registrar.setUseIsoFormat(true);
		registrar.registerFormatters(registry);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedHeaders("Access-Control-Allow-Headers", "Authorization", "Content-Type",
					"Access-Control-Max-Age")
				.allowedMethods("POST", "PUT", "PATCH", "GET", "DELETE");
	}
}
