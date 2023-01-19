package com.calendar.tft.match.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.calendar.tft.match.service.dto.MatchCriteria;
import com.calendar.tft.match.service.dto.MatchDto;
import com.calendar.tft.match.entity.MatchRaw;
import com.calendar.tft.match.repository.MatchRawRepository;
import com.calendar.tft.summoner.entity.Summoner;
import com.calendar.tft.summoner.repository.SummonerRepository;
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

	private final SummonerRepository summonerRepository;

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
	public void fetchAndSaveMatchRaws(Summoner summoner) throws InterruptedException {
		long startTimeInSeconds = summoner.getLastFetchedAt().getEpochSecond() + 2;

		while (true) {
			// 매치 ID들 조회
			MatchCriteria matchCriteria = new MatchCriteria(0, startTimeInSeconds, null, 99);
			List<String> matchIds = this.fetchMatchIdsByPuuid(summoner.getPuuid(), matchCriteria);

			if (matchIds.isEmpty()) {
				return;
			}

			MatchRaw lastMatchRaw = null;

			// 매치 상세 조회
			for (String matchId : matchIds) {
				MatchDto matchDto = this.fetchMatchById(matchId);
				MatchRaw matchRaw = matchDto.toMatchRaw();

				matchRawRepository.save(matchRaw);
				lastMatchRaw = matchRaw;

				System.out.println(matchId + " inserted!!");
			}

			// 소환사의 마지막 불러온 데이터 업데이트
			summoner.updateLastFetched(null, Instant.ofEpochMilli(lastMatchRaw.getGameDatetimeInMillis()));
			summonerRepository.save(summoner);

			startTimeInSeconds = summoner.getLastFetchedAt().getEpochSecond();

			// 100초간 휴식
			Thread.sleep(100_000);
		}
	}
}
