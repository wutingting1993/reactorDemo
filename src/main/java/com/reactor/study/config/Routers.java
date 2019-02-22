package com.reactor.study.config;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.reactor.study.model.ApiSetting;

@Configuration
public class Routers {

	@Bean
	public RouterFunction<ServerResponse> routes() {
		return route(GET("/"), (ServerRequest req) -> ok()
			.body(BodyInserters.fromObject(Arrays.asList(ApiSetting.builder().apiKeys("1").apis("1").defaultGatewayResponses("1").build(),
				ApiSetting.builder().apiKeys("2").apis("2").defaultGatewayResponses("2").build()))));
	}
}
