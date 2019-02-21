package com.reactor.study.controller;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.reactor.study.StudyApplication;
import com.reactor.study.service.ApiService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = StudyApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class WebFluxTestControllerTest2 {

	@Autowired
	private ApiService apiService;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void reduce2Test() {
		log.warn(apiService.toString());
		String data = "{\"apis\":\"apis\",\"apiKeys\":\"apiKeys\",\"defaultGatewayResponses\":\"defaultGatewayResponses\"}";
		WebTestClient.ResponseSpec responseSpec = webTestClient.get().uri("/webflux/apiSetting8").exchange();
		responseSpec.expectStatus().isOk().expectBody().json(data);
	}

	@Test
	public void routesTest() {
		String data = "[{\"apis\":\"1\",\"apiKeys\":\"1\",\"defaultGatewayResponses\":\"1\"},{\"apis\":\"2\",\"apiKeys\":\"2\",\"defaultGatewayResponses\":\"2\"}]\n";
		WebTestClient.ResponseSpec responseSpec = webTestClient.get().uri("/").exchange();
		responseSpec.expectStatus().isOk().expectBody().json(data);
	}
}
