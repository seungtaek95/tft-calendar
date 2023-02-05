package com.calendar.tft.matchStat.service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import com.calendar.tft.match.repository.MatchResultRepository;
import com.calendar.tft.match.service.dto.MatchResultOfSummoner;
import com.calendar.tft.matchStat.entity.DailyMatchStat;
import com.calendar.tft.matchStat.entity.MonthlyMatchStat;
import com.calendar.tft.matchStat.repository.MonthlyMatchStatRepository;
import com.calendar.tft.summoner.entity.Summoner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchStatServiceImpl implements MatchStatService {
	private final MatchResultRepository matchResultRepository;
	private final MonthlyMatchStatRepository monthlyMatchStatRepository;

	@Override
	public void renewStatistics(Summoner summoner) {
		List<MonthlyMatchStat> monthlyMatchStatsToSave = new LinkedList<>();

		Instant lastStatCalculatedAt = monthlyMatchStatRepository.findFirstByPuuidOrderByIdDesc(summoner.getPuuid()).map(MonthlyMatchStat::getLastUpdatedAt)
			.orElse(Instant.ofEpochMilli(0L));

		List<MatchResultOfSummoner> matchResults = matchResultRepository.getMatchResultsBy(summoner.getSummonerNo(), lastStatCalculatedAt);

		// 연도별 MatchResult 들
		Map<Integer, List<MatchResultOfSummoner>> matchResultsByYear = matchResults.stream().collect(Collectors.groupingBy(MatchResultOfSummoner::year, TreeMap::new, Collectors.toList()));
		for (int year: matchResultsByYear.keySet()) {
			// 월별 MatchResults
			Map<Integer, List<MatchResultOfSummoner>> matchResultsByMonth = matchResultsByYear.get(year).stream().collect(Collectors.groupingBy(MatchResultOfSummoner::month, TreeMap::new, Collectors.toList()));
			for (int month : matchResultsByMonth.keySet()) {
				// 기존재 월간 매치 통계
				MonthlyMatchStat monthlyMatchStat = monthlyMatchStatRepository.findOneByPuuidAndYearAndMonth(summoner.getPuuid(), year, month)
					.orElse(new MonthlyMatchStat(summoner.getPuuid(), year, month, new LinkedList<>()));

				// 일별 MatchResults
				Map<Integer, List<MatchResultOfSummoner>> dailyMatchResultsByDayOfMonth = matchResultsByMonth.get(month).stream().collect(Collectors.groupingBy(MatchResultOfSummoner::dayOfMonth, TreeMap::new, Collectors.toList()));
				for (int dayOfMonth : dailyMatchResultsByDayOfMonth.keySet()) {
					monthlyMatchStat.accumulateOrAddDailyStat(DailyMatchStat.from(dayOfMonth, dailyMatchResultsByDayOfMonth.get(dayOfMonth)));
				}

				monthlyMatchStatsToSave.add(monthlyMatchStat);
			}
		}

		// 월간 매치 통계 저장
		monthlyMatchStatRepository.saveAll(monthlyMatchStatsToSave);
	}
}
