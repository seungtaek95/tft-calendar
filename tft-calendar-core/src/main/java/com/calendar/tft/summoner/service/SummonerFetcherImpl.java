package com.calendar.tft.summoner.service;

import com.calendar.tft.summoner.service.dto.SummonerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class SummonerFetcherImpl implements SummonerFetcher {
	@Qualifier("KoreaApiClient")
	private final WebClient webClient;

	@Override
	public SummonerDto fetchSummonerByPuuid(String puuid) {
		return webClient
			.get()
			.uri("/tft/summoner/v1/summoners/by-puuid/{puuid}", puuid)
			.retrieve()
			.bodyToMono(SummonerDto.class)
			.log()
			.block();
	}
}
