package com.calendar.tft.match.service;

import java.util.List;
import java.util.Objects;

import com.calendar.tft.match.service.dto.MatchCriteria;
import com.calendar.tft.match.service.dto.MatchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MatchFetcherImpl implements MatchFetcher {
	@Qualifier("AsiaApiClient")
	private final WebClient webClient;

	@Override
	public Mono<List<String>> fetchMatchIdsByPuuid(String puuid, MatchCriteria matchCriteria) {
		Objects.requireNonNull(matchCriteria);

		return webClient
			.get()
			.uri(uriBuilder -> uriBuilder
				.path("/tft/match/v1/matches/by-puuid/{puuid}/ids")
				.queryParam("count", matchCriteria.count())
				.queryParam("start", matchCriteria.startIndex())
				.queryParam("startTime", matchCriteria.startTimeInSeconds())
				.queryParam("endTime", matchCriteria.endTimeInSeconds())
				.build(puuid)
			)
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<>() {});
	}

	@Override
	public Mono<MatchDto> fetchMatchById(String matchId) {
		return webClient
			.get()
			.uri("/tft/match/v1/matches/{matchId}", matchId)
			.retrieve()
			.bodyToMono(MatchDto.class);
	}
}
