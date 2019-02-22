package com.reactor.study.config;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Getter
@Component
@Slf4j
public class MyClient {
	private final WebClient webClient;
	private final ObjectMapper objectMapper;

	public MyClient(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
		this.webClient = webClientBuilder.baseUrl("url").filter(logRequest()).filter(logResponse()).build();
		this.objectMapper = objectMapper;
	}

	private ExchangeFilterFunction logRequest() {
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			log.info("Request: {} {} {} {}", clientRequest.method(), clientRequest.url());
			clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("Header {}={}", name, value)));
			clientRequest.cookies().forEach((name, values) -> values.forEach(value -> log.info("Cookie {}={}", name, value)));
			return Mono.just(clientRequest);
		});
	}

	private ExchangeFilterFunction logResponse() {
		return ExchangeFilterFunction.ofResponseProcessor(Mono::just);
	}

}
