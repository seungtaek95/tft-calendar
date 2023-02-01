package com.calendar.tft.matchStat.service;

import java.time.Instant;
import java.util.*;

import com.calendar.tft.match.repository.MatchResultRepository;
import com.calendar.tft.match.service.dto.MatchResultOfSummoner;
import com.calendar.tft.matchStat.entity.DailyMatchStat;
import com.calendar.tft.matchStat.entity.MonthlyMatchStat;
import com.calendar.tft.matchStat.repository.MonthlyMatchStatRepository;
import com.calendar.tft.summoner.entity.Summoner;
import com.calendar.tft.summoner.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchStatServiceImpl implements MatchStatService {
	private final SummonerRepository summonerRepository;
	private final MatchResultRepository matchResultRepository;
	private final MonthlyMatchStatRepository monthlyMatchStatRepository;

	@Override
	public void renewStatisticsOf(Summoner summoner) {
		List<MonthlyMatchStat> monthlyMatchStatsToSave = new LinkedList<>();

		Instant lastStatCalculatedAt = summoner.getLastStatCalculatedAt() != null
			? summoner.getLastStatCalculatedAt()
			: Instant.ofEpochMilli(0L);

		List<MatchResultOfSummoner> matchResults = matchResultRepository.getMatchResultsBy(summoner.getSummonerNo(), lastStatCalculatedAt);

		// 연도별 MatchResult 들
		Map<Integer, List<MatchResultOfSummoner>> matchResultsByYear = new HashMap<>();
		for (MatchResultOfSummoner matchResult : matchResults) {
			matchResultsByYear.putIfAbsent(matchResult.year(), new LinkedList<>());
			matchResultsByYear.get(matchResult.year()).add(matchResult);
		}

		for (int year : matchResultsByYear.keySet()) {
			// 월별 MatchResult 들
			Map<Integer, List<MatchResultOfSummoner>> matchResultsByMonth = new HashMap<>();
			for (MatchResultOfSummoner matchResult : matchResultsByYear.get(year)) {
				matchResultsByMonth.putIfAbsent(matchResult.month(), new LinkedList<>());
				matchResultsByMonth.get(matchResult.month()).add(matchResult);
			}

			for (int month : matchResultsByMonth.keySet()) {
				MonthlyMatchStat monthlyMatchStat = monthlyMatchStatRepository.findByPuuidAndYearAndMonth(summoner.getPuuid(), year, month)
					.orElseGet(() -> new MonthlyMatchStat(summoner.getPuuid(), year, month));

				// 일별 MatchResult 들
				Map<Integer, List<MatchResultOfSummoner>> dailyMatchResultsByDayOfMonth = new HashMap<>();
				for (MatchResultOfSummoner matchResult : matchResultsByMonth.get(month)) {
					dailyMatchResultsByDayOfMonth.putIfAbsent(matchResult.dayOfMonth(), new LinkedList<>());
					dailyMatchResultsByDayOfMonth.get(matchResult.dayOfMonth()).add(matchResult);
				}

				for (int dayOfMonth : dailyMatchResultsByDayOfMonth.keySet()) {
					monthlyMatchStat.accumulateDailyStat(DailyMatchStat.from(dayOfMonth, dailyMatchResultsByDayOfMonth.get(dayOfMonth)));
				}

				monthlyMatchStatsToSave.add(monthlyMatchStat);
			}
		}

		// 월간 매치 통계 저장
		monthlyMatchStatRepository.saveAll(monthlyMatchStatsToSave);

		// 소환사의 마지막 통계 계산 시간 업데이트
		summoner.updateLastStatCalculatedAt(Instant.now());
		summonerRepository.save(summoner); // TODO: Transactional 필요성. monthlyMatchStat에 lastMatchId를 넣을까?
	}
}
