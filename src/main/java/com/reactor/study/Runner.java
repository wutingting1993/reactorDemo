package com.reactor.study;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;

import com.reactor.study.model.ApiSetting;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Configuration
public class Runner implements CommandLineRunner {

	@Autowired
	private MongoOperations operations;

	@Override
	public void run(String... args) {
		operations.dropCollection(ApiSetting.class);
		operations.createCollection(ApiSetting.class, CollectionOptions.empty().size(1000).capped());

		Flux.just(ApiSetting.builder().apiKeys("1").apis("1").defaultGatewayResponses("1").build(),
			ApiSetting.builder().apiKeys("2").apis("2").defaultGatewayResponses("2").build())
			.doOnNext(operations::save)
			.blockLast();

		log.info("finish init");

	}
}
