package com.calendar.tft.match.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.calendar.tft.match.domain.entity.Match;
import com.calendar.tft.match.repository.MatchRepository;
import com.calendar.tft.match.service.dto.MatchCriteria;
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
	public List<Match> fetchAndSaveMatchData(Summoner summoner, MatchCriteria matchCriteria) {
		// 매치 ID들 조회
		List<String> matchIds = matchFetcher.fetchMatchIdsByPuuid(summoner.getPuuid(), matchCriteria).block();

		// 새로운 매치가 없으면 종료
		if (matchIds == null || matchIds.isEmpty()) {
			return Collections.unmodifiableList(new LinkedList<>());
		}

		// 매치 ID로 매치 상세 조회
		// TODO: DB에 매치 정보가 있다면 API 호출 대상X
		List<Match> fetchedMatches = Flux.fromIterable(matchIds)
			.flatMap(matchFetcher::fetchMatchById) // TODO: 일부 성공 + too many request 에러 대응
			.map(matchDto -> matchDto.toMatchOf(summoner)) // TODO: 모든 사용자의 MatchResult를 저장
			.collectList()
			.block();

		if (fetchedMatches == null) {
			throw new RuntimeException();
		}

		// 매치 엔티티 저장
		matchRepository.saveAll(fetchedMatches);

		return Collections.unmodifiableList(fetchedMatches);
	}
}
