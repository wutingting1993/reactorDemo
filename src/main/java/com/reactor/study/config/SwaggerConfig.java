package com.reactor.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@AllArgsConstructor
@Configuration
@EnableSwagger2WebFlux
public class SwaggerConfig {

	@Bean
	public Docket produceApi() {
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(apiInfo())
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.reactor.study.controller"))
			.paths(PathSelectors.any())
			.build()
			.genericModelSubstitutes(Flux.class, Mono.class);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("reactor example API")
			.description("")
			.version("0.0.1-SNAPSHOT")
			.build();
	}
}