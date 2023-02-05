package com.calendar.tft.match.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.calendar.tft.match.domain.entity.Match;
import com.calendar.tft.match.service.dto.MatchCriteria;
import com.calendar.tft.match.service.dto.MatchRenewResult;
import com.calendar.tft.matchStat.service.MatchStatService;
import com.calendar.tft.summoner.entity.Summoner;
import com.calendar.tft.summoner.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchRenewServiceImpl implements MatchRenewService {
	private final int CONCURRENCY_LEVEL = 10;
	private final MatchFetchService matchFetchService;
	private final MatchRenewQueue matchRenewQueue;
	private final SummonerRepository summonerRepository;
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
	public void renew(Summoner summoner) throws InterruptedException {
		Instant matchFetchedAt = Instant.now();

		// 소환사의 마지막 매치 조회 시간을 가져와서 조회 시작 시간으로 설정
		long startTimeInSeconds = summoner.getLastFetchedAt().map(Instant::getEpochSecond)
			.orElseGet(() -> Instant.now().getEpochSecond());
		long endTimeInSeconds = Instant.now().getEpochSecond();

		// 매치 조회 및 저장
		while (true) {
			MatchCriteria matchCriteria = new MatchCriteria(null, startTimeInSeconds, endTimeInSeconds, CONCURRENCY_LEVEL);
			List<Match> fetchedMatches = matchFetchService.fetchAndSaveMatchData(summoner, matchCriteria);
			if (fetchedMatches.isEmpty() || fetchedMatches.size() < CONCURRENCY_LEVEL) {
				break;
			}

			// 다음 매치부터 가져오기 위해 endTime 설정
			endTimeInSeconds = fetchedMatches.get(fetchedMatches.size() - 1).getPlayedAt().getEpochSecond() - 1;

			Thread.sleep(15_000);
		}

		// 소환사의 마지막 매치 조회 시간 업데이트
		summoner.updateLastFetchedAt(matchFetchedAt);
		summonerRepository.save(summoner);

		// 매치 통계 업데이트
		matchStatService.renewStatistics(summoner);
	}
}
