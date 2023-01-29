package com.calendar.tft.match.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.calendar.tft.match.domain.entity.Match;
import com.calendar.tft.match.repository.MatchRepository;
import com.calendar.tft.match.service.dto.MatchCriteria;
import com.calendar.tft.match.service.dto.MatchRenewResult;
import com.calendar.tft.matchStat.service.MatchStatService;
import com.calendar.tft.summoner.entity.Summoner;
import com.calendar.tft.summoner.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class MatchRenewServiceImpl implements MatchRenewService {
	private final int RATE_LIMIT_SECONDS = 120_000; // 2분
	private final int RATE_LIMIT_REQUESTS = 100; // 100개
	private final MatchFetcher matchFetcher;
	private final MatchRenewQueue matchRenewQueue;
	private final SummonerRepository summonerRepository;
	private final MatchRepository matchRepository;
	private final MatchStatService matchStatService;

	public MatchRenewResult tryRenew(Summoner summoner) {
		// 최근에 갱신했다면 return
		if (summoner.isRecentlyRenewed()) {
			return MatchRenewResult.recentlyRenewed(summoner.getPuuid());
		}

		// 이미 대기 큐에 있으면 return
		boolean isPuuidInWaitingQueue = matchRenewQueue.isPuuidInWaitingQueue(summoner.getPuuid());
		if (isPuuidInWaitingQueue) {
			return MatchRenewResult.alreadyProcessing(summoner.getPuuid());
		}

		// 현재 작업중 큐에 있으면 return
		boolean isPuuidInProcessingQueue = matchRenewQueue.isPuuidInProcessingQueue(summoner.getPuuid());
		if (isPuuidInProcessingQueue) {
			return MatchRenewResult.alreadyProcessing(summoner.getPuuid());
		}

		matchRenewQueue.addToWaitingQueue(summoner.getPuuid());

		return MatchRenewResult.queued(summoner.getPuuid());
	}

	@Override
	public Optional<Summoner> getOldestWaitingSummoner() {
		Optional<String> result = matchRenewQueue.getOldestPuuidFromWaitingQueue();
		if (result.isEmpty()) {
			return Optional.empty();
		}

		return summonerRepository.findByPuuid(result.get());
	}

	@Override
	@Transactional
	public void renew(Summoner summoner) {
		try {
			// 매치 조회 및 저장
			this.fetchAndSaveMatchRaws(summoner);
			// 매치 통계 업데이트
			// 소환사의 마지막 갱신 시간 업데이트
		} catch (Exception e) {
			System.out.println("이게 뭐람");
		}
	}

	private void fetchAndSaveMatchRaws(Summoner summoner) throws InterruptedException {
		while (true) {
			long startTimeInSeconds = summoner.getLastFetchedAt() != null
				? summoner.getLastFetchedAt().getEpochSecond() + 2 // 소환사의 마지막 매치 ID로 매치 정보 가져와서 시간으로 설정
				: 0L;

			// 매치 ID들 조회
			MatchCriteria matchCriteria = new MatchCriteria(0, startTimeInSeconds, null, RATE_LIMIT_REQUESTS - 1);
			List<String> matchIds = matchFetcher.fetchMatchIdsByPuuid(summoner.getPuuid(), matchCriteria).block();

			summoner.updateLastFetchedAt(Instant.now());

			// 새로운 매치가 없으면 종료
			if (matchIds == null || matchIds.isEmpty()) {
				summonerRepository.save(summoner);
				return;
			}

			// 매치 ID로 매치 상세 조회
			List<Match> matches = Flux.fromIterable(matchIds)
				.flatMap(matchFetcher::fetchMatchById)
				.map(matchDto -> matchDto.toMatchOf(summoner))
				.collectList()
				.block();

			if (matches == null) {
				throw new RuntimeException();
			}

			// 매치 엔티티 저장
			matchRepository.saveAll(matches);

			// 소환사의 마지막 불러온 데이터 업데이트
			Match lastMatch = matches.get(matches.size() - 1);
			summoner.updateLastFetchedMatchId(lastMatch.getMatchId());
			summonerRepository.save(summoner);

			// 120초간 휴식
			Thread.sleep(RATE_LIMIT_SECONDS);
		}
	}
}
