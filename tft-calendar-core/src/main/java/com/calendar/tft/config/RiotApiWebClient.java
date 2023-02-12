package com.calendar.tft.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class RiotApiWebClient {
	private final String riotToken;

	public RiotApiWebClient(
			@Value("${riot.api.token}") String riotToken) {
		this.riotToken = riotToken;
	}

	@Bean
	public ConnectionProvider connectionProvider() {
		return ConnectionProvider.builder("connection-pool")
			.maxConnections(20)
			.maxIdleTime(Duration.ofMillis(10_000))
			.build();
	}

	@Bean
	public HttpClient httpClient(ConnectionProvider connectionProvider) {
		return HttpClient.create(connectionProvider);
	}

	@Bean
	@Qualifier("AsiaApiClient")
	public WebClient asiaWebClient(HttpClient httpClient) {
		return WebClient.builder()
			.clientConnector(new ReactorClientHttpConnector(httpClient))
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
