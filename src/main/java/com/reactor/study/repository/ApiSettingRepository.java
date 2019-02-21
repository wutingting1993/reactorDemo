package com.reactor.study.repository;

import org.springframework.stereotype.Component;

import com.reactor.study.model.ApiSetting;

@Component
public class ApiSettingRepository {

	public ApiSetting getApiSetting() {
		return ApiSetting.builder().apiKeys("apiKeys").apis("apis").defaultGatewayResponses("defaultGatewayResponses").build();
	}
}
