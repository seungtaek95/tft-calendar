package com.tft.calendar.match.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.tft.calendar.match.service.dto.MatchCriteria;
import com.tft.calendar.match.service.dto.MatchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class MatchFetcherImpl implements MatchFetcher {
	@Qualifier("AsiaApiClient")
	private final WebClient webClient;

	@Override
	public List<String> fetchMatchIdsByPuuid(String puuid, MatchCriteria matchCriteria) {
		Objects.requireNonNull(matchCriteria);

		String[] result = webClient
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
			.bodyToMono(String[].class)
			.log()
			.block();

		return result == null ? Collections.unmodifiableList(new ArrayList<>()) : List.of(result);
	}

	@Override
	public MatchDto fetchMatchById(String matchId) {
		return webClient
			.get()
			.uri("/tft/match/v1/matches/{matchId}", matchId)
			.retrieve()
			.bodyToMono(MatchDto.class)
			.log()
			.block();
	}
}
