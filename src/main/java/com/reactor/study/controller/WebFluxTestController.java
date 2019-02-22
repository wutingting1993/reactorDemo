package com.reactor.study.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactor.study.model.ApiSetting;
import com.reactor.study.service.ApiService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/webflux")
@RestController
public class WebFluxTestController {

	@Autowired
	private ApiService apiService;

	@GetMapping("mono")
	public Mono<String> mono() {
		return Mono.just("hello, mono");
	}

	@GetMapping("flux")
	public Flux<String> flux() {
		return Flux.fromArray(new String[] {"1", "2", "3"});
	}

	@GetMapping("flux2")
	public Flux<String> flux2() {
		return Flux.just("zhangsan", "lisi");
	}

	@GetMapping("apiSetting")
	public Mono<ApiSetting> getApiSetting() {
		return apiService.getApiSetting();
	}

	@GetMapping("apiSetting2")
	public Mono<ApiSetting> getApiSetting2() {
		return apiService.getApiSetting2();
	}

	@GetMapping("not-found")
	public Mono<ApiSetting> notFound() {
		return apiService.getApiSetting3();
	}

	@GetMapping("apiSetting4")
	public Mono<String> getApiSetting4() {
		return apiService.getApiSetting4();
	}

	@GetMapping("apiSetting5")
	public Mono<String> getApiSetting5() {
		return apiService.getApiSetting5();
	}

	@GetMapping("apiSetting6")
	public Mono<List> getApiSetting6() {
		return apiService.getApiSetting6();
	}

	@GetMapping("apiSetting7")
	public Mono<List> getApiSetting7() {
		return apiService.getApiSetting7();
	}

	@GetMapping("apiSetting8")
	public Mono<ApiSetting> getApiSetting8() {
		return apiService.getApiSetting8();
	}

	@GetMapping("apiSettings")
	public Flux<ApiSetting> getAllApiSettings() {
		return apiService.getAllApiSettings();
	}

	@GetMapping("reduce")
	public Mono<String> reduce() {
		List<String> strs = new ArrayList<>();
		strs.add("aaaa");
		strs.add("bbb");
		strs.add("ccc");
		return Flux.fromIterable(strs).reduce(String::concat);
	}

	@GetMapping("reduce2")
	public Mono<String> reduce2() {
		List<String> strs = new ArrayList<>();
		strs.add("aaaa");
		strs.add("bbb");
		strs.add("ccc");
		return Flux.fromIterable(strs).reduce("ddd", String::concat);
	}

	@GetMapping("subscribe")
	public void subscribe() {
		List<String> strs = new ArrayList<>();
		strs.add("aaaa");
		strs.add("bbb");
		strs.add("ccc");
		Flux.fromIterable(strs).subscribe(System.out::println);
	}
}
