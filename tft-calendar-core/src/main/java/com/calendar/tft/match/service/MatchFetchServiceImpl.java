package com.calendar.tft.match.service;

import java.util.*;
import java.util.stream.Collectors;

import com.calendar.tft.match.domain.entity.Match;
import com.calendar.tft.match.repository.MatchRepository;
import com.calendar.tft.match.service.dto.FetchAndSaveMatchResult;
import com.calendar.tft.match.service.dto.MatchCriteria;
import com.calendar.tft.match.service.dto.MatchDto;
import com.calendar.tft.summoner.entity.Summoner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class MatchFetchServiceImpl implements MatchFetchService {
	private final MatchFetcher matchFetcher;
	private final MatchRepository matchRepository;

	@Override
	public FetchAndSaveMatchResult fetchAndSaveMatchData(Summoner summoner, MatchCriteria matchCriteria) {
		// 매치 ID들 조회
		List<String> matchIds = matchFetcher.fetchMatchIdsByPuuid(summoner.getPuuid(), matchCriteria).block();

		// 새로운 매치가 없으면 종료
		if (matchIds == null || matchIds.isEmpty()) {
			return new FetchAndSaveMatchResult(matchIds, new LinkedList<>(), new LinkedList<>());
		}

		// 기존에 저장된 매치 정보가 있는지 조회
		List<Match> existMatches = matchRepository.findAllByMatchIdIn(matchIds);
		Set<String> existMatchIds = existMatches.stream().map(Match::getMatchId).collect(Collectors.toUnmodifiableSet());

		// 기존에 저장되지 않은 매치 id들로 매치 상세 조회
		List<Match> fetchedMatches = Flux.fromIterable(matchIds)
			.filter(matchId -> !existMatchIds.contains(matchId))
			.flatMap(matchFetcher::fetchMatchById) // TODO: 일부 성공 + too many request 에러 대응
			.map(MatchDto::toMatch)
			.collectList()
			.block();

		if (fetchedMatches == null) {
			throw new RuntimeException();
		}

		// 매치 엔티티 저장
		matchRepository.saveAllIgnoreDuplicate(fetchedMatches);

		return new FetchAndSaveMatchResult(matchIds, existMatches, fetchedMatches);
	}
}
