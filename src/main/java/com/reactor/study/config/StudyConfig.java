package com.reactor.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class StudyConfig {

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}