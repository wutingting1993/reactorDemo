package com.reactor.study.model;

import io.github.classgraph.json.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ApiSetting {
	@Id
	private String apis;
	private String apiKeys;
	private String defaultGatewayResponses;
}
