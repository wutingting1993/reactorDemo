package com.reactor.study.controller;

import static org.mockito.BDDMockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.reactor.study.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @WebFluxTest helps to test Spring WebFlux controllers with auto-configuring the Spring WebFlux infrastructure,
 * limit scanned beans like {@Controller, @ControllerAdvice, @JsonComponent, WebFluxConfigurer} and never scan @Component beans.
 *
 * Typically {@code @WebFluxTest} is used in combination with {@link MockBean @MockBean}
 * or {@link Import @Import} to create any collaborators required by your
 * {@code @Controller} beans.
 */

@RunWith(SpringRunner.class)
@WebFluxTest
@Slf4j
public class WebFluxTestControllerTest {

	@MockBean
	private ApiService apiService;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void reduce2Test() {
		log.warn(apiService.toString());
		String data = "{\"key\":\"this is api setting\"}";
		given(apiService.getApiSetting5()).willReturn(Mono.just(data));
		WebTestClient.ResponseSpec responseSpec = webTestClient.get().uri("/webflux/apiSetting5").exchange();
		responseSpec.expectStatus().isOk().expectBody().json(data);
	}

	//	@Test
	//	public void routesTest() {
	//		String data = "[{\"apis\":\"1\",\"apiKeys\":\"1\",\"defaultGatewayResponses\":\"1\"},{\"apis\":\"2\",\"apiKeys\":\"2\",\"defaultGatewayResponses\":\"2\"}]\n";
	//		WebTestClient.ResponseSpec responseSpec = webTestClient.get().uri("/").exchange();
	//		responseSpec.expectStatus().isOk().expectBody().json(data);
	//	}
}
