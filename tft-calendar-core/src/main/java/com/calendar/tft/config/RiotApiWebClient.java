package com.calendar.tft.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RiotApiWebClient {
	private final String riotToken;

	public RiotApiWebClient(
			@Value("${riot.api.token}") String riotToken) {
		this.riotToken = riotToken;
	}

	@Bean
	@Qualifier("AsiaApiClient")
	public WebClient asiaWebClient() {
		return WebClient.builder()
			.baseUrl("https://asia.api.riotgames.com")
			.defaultHeader("X-Riot-Token", riotToken)
			.build();
	}

	@Bean
	@Qualifier("KoreaApiClient")
	public WebClient koreaWebClient() {
		return WebClient.builder()
			.baseUrl("https://kr.api.riotgames.com")
			.defaultHeader("X-Riot-Token", riotToken)
			.build();
	}
}
