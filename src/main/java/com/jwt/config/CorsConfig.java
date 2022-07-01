package com.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class CorsConfig {
	
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		CorsConfiguration config = new CorsConfiguration();
		
		config.setAllowCredentials(true); // 내서버가 응답 할때 자바스크립트에서 처리 할수 있게 할지 설정 하는거 
		config.addAllowedOrigin("*"); // 모든 ip에 응답 허용
		config.addAllowedHeader("*"); // 모든 헤더에 응답 허용
		config.addAllowedMethod("*"); // 모든 post , get , put 허용 
		
		source.registerCorsConfiguration("/api/**", config);
		
		return new CorsFilter(source);
	}
	
}
