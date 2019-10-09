package com.reactor.study.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class MyFilter implements WebFilter {
	@Override
	public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
		log.info("Request url:{}, formData：{}，QueryParams：{}", serverWebExchange.getRequest().getURI(), serverWebExchange.getFormData(), serverWebExchange.getRequest().getQueryParams());
		Mono<Void> filter = webFilterChain.filter(serverWebExchange);
		log.info("Response {}", serverWebExchange.getResponse());
		return filter;
	}
}
