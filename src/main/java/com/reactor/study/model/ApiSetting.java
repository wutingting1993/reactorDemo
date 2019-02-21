package com.reactor.study.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ApiSetting {
	private String apis;
	private String apiKeys;
	private String defaultGatewayResponses;
}
