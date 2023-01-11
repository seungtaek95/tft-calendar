package com.tft.calendar.match.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import com.tft.calendar.match.entity.MatchRaw;
import com.tft.calendar.match.repository.MatchRawRepository;
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

	private final MatchRawRepository matchRawRepository;

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

	@Override
	public int fetchAndSaveMatchRaws(String puuid, long coolTimeMillis) throws InterruptedException {
		int startIndex = 0;

		while(true) {
			AtomicInteger matchCount = new AtomicInteger();

			MatchCriteria matchCriteria = new MatchCriteria(startIndex, null, null, 99);
			List<String> matchIds = this.fetchMatchIdsByPuuid(puuid, matchCriteria);

			if (matchIds.isEmpty()) {
				return matchCount.get();
			}

			matchIds.forEach(matchId -> {
				MatchDto matchDto = this.fetchMatchById(matchId);
				MatchRaw matchRaw = matchDto.toMatchRaw();

				matchRawRepository.save(matchRaw);
				matchCount.addAndGet(1);

				System.out.println(matchId + " inserted!!");
			});

			startIndex += 99;

			// 2분간 휴식
			Thread.sleep(coolTimeMillis);
		}
	}
}
