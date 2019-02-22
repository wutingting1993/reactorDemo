package com.reactor.study.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.reactor.study.model.ApiSetting;
import reactor.core.publisher.Flux;

public interface ApiRepository extends ReactiveMongoRepository<ApiSetting, String> {

	Flux<ApiSetting> findApiSettingsBy();
}
