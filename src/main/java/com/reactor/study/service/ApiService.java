package com.reactor.study.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import com.reactor.study.config.MyClient;
import com.reactor.study.model.ApiSetting;
import com.reactor.study.repository.ApiRepository;
import com.reactor.study.repository.ApiSettingRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

@Slf4j
@Service
public class ApiService {
	@Autowired
	private MyClient webClient;

	private static ExchangeFilterFunction logRequest() {
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
			clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
			return Mono.just(clientRequest);
		});
	}

	@Autowired
	private ApiSettingRepository apiSettingRepository;

	@Autowired
	private ApiRepository apiRepository;

	private AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

	public Mono<ApiSetting> getApiSetting() {
		return webClient.getWebClient().get().uri("/synchronizations?regionType=KR")
			.exchange().flatMap(clientResponse -> clientResponse.bodyToMono(ApiSetting.class));
	}

	public Mono<ApiSetting> getApiSetting2() {
		return Mono.create(apiSettingMonoSink -> {
			ListenableFuture<ResponseEntity<ApiSetting>> entity = asyncRestTemplate.getForEntity("url", ApiSetting.class);
			entity.addCallback(getListenableFutureCallback(apiSettingMonoSink));

		});
	}

	public Mono<ApiSetting> getApiSetting3() {
		return Mono.create(apiSettingMonoSink -> {
			ListenableFuture<ResponseEntity<ApiSetting>> entity = asyncRestTemplate.getForEntity("url", ApiSetting.class);
			entity.addCallback(getListenableFutureCallback(apiSettingMonoSink));

		});
	}

	public Mono<String> getApiSetting4() {
		return Mono.just(apiSettingRepository.getApiSetting())
			.map(apiSetting -> apiSetting.getApiKeys() + apiSetting.getApis() + apiSetting.getDefaultGatewayResponses());
	}

	public Mono<String> getApiSetting5() {
		return Mono.just(apiSettingRepository.getApiSetting())
			.flatMap(apiSetting -> Mono.just(apiSetting.getApiKeys() + apiSetting.getApis() + apiSetting.getDefaultGatewayResponses()))
			.flatMap(v -> Mono.just(v + ",abc"))
			.map(v -> v + ",def");
	}

	public Mono<List> getApiSetting6() {
		Mono<ApiSetting> mono1 = webClient.getWebClient().get().uri("/synchronizations?regionType=KR")
			.exchange().flatMap(clientResponse -> clientResponse.bodyToMono(ApiSetting.class));
		Mono<ApiSetting> mono2 = webClient.getWebClient().get().uri("/synchronizations?regionType=KR")
			.exchange().flatMap(clientResponse -> clientResponse.bodyToMono(ApiSetting.class));

		return Mono.zip(items -> {
			List results = new ArrayList();
			ApiSetting apiSetting1 = (ApiSetting)items[0];
			results.add(apiSetting1);
			ApiSetting apiSetting2 = (ApiSetting)items[1];
			results.add(apiSetting2);
			return results;
		}, mono1, mono2);
	}

	public Mono<List> getApiSetting7() {
		Mono<ApiSetting> mono1 = webClient.getWebClient().get().uri("/synchronizations?regionType=KR")
			.exchange().flatMap(clientResponse -> clientResponse.bodyToMono(ApiSetting.class));
		Mono<ApiSetting> mono2 = webClient.getWebClient().get().uri("/synchronizations?regionType=KR")
			.exchange().flatMap(clientResponse -> clientResponse.bodyToMono(ApiSetting.class));

		return Mono.zip(mono1, mono2).map(tuple -> {
			List results = new ArrayList();
			results.add(tuple.getT1());
			results.add(tuple.getT2());
			return results;
		});
	}

	public Mono<ApiSetting> getApiSetting8() {
		return Mono.just(apiSettingRepository.getApiSetting());
	}

	private ListenableFutureCallback<ResponseEntity<ApiSetting>> getListenableFutureCallback(MonoSink<ApiSetting> apiSettingMonoSink) {
		return new ListenableFutureCallback<ResponseEntity<ApiSetting>>() {
			@Override
			public void onFailure(Throwable throwable) {
				apiSettingMonoSink.error(throwable);
			}

			@Override
			public void onSuccess(ResponseEntity<ApiSetting> apiSettingResponseEntity) {
				apiSettingMonoSink.success(apiSettingResponseEntity.getBody());
			}
		};
	}

	public Flux<ApiSetting> getAllApiSettings() {
		return apiRepository.findAll();
	}

	public Mono<ApiSetting> getApiSettingById(String id) {
		return apiRepository.findById(id);
	}
}
