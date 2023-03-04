package com.calendar.tft.matchStat.service;

import java.util.*;
import java.util.stream.Collectors;

import com.calendar.tft.match.domain.entity.Match;
import com.calendar.tft.matchStat.entity.DailyMatchStat;
import com.calendar.tft.matchStat.entity.MonthlyMatchStat;
import com.calendar.tft.matchStat.repository.MonthlyMatchStatRepository;
import com.calendar.tft.summoner.entity.Summoner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchStatServiceImpl implements MatchStatService {
	private final MonthlyMatchStatRepository monthlyMatchStatRepository;

	@Override
	public void renewStatistics(Summoner summoner, List<Match> matches) {
		List<MonthlyMatchStat> calculatedMonthlyMatchStats = this.calculateStat(summoner, matches);

		monthlyMatchStatRepository.saveAll(calculatedMonthlyMatchStats);
	}

	private List<MonthlyMatchStat> calculateStat(Summoner summoner, List<Match> matches) {
		List<MonthlyMatchStat> calculatedMonthlyMatchStats = new LinkedList<>();

		// 연도별 Match 들
		Map<Integer, List<Match>> matchesByYear = matches.stream().collect(Collectors.groupingBy(Match::year, TreeMap::new, Collectors.toList()));
		for (int year: matchesByYear.keySet()) {
			// 월별 MatchResults
			Map<Integer, List<Match>> matchesByMonth = matchesByYear.get(year).stream().collect(Collectors.groupingBy(Match::month, TreeMap::new, Collectors.toList()));
			for (int month : matchesByMonth.keySet()) {
				// 기존재 월간 매치 통계
				MonthlyMatchStat monthlyMatchStat = monthlyMatchStatRepository.findOneByPuuidAndYearAndMonth(summoner.getPuuid(), year, month)
					.orElse(new MonthlyMatchStat(summoner.getPuuid(), year, month, new LinkedList<>()));

				// 일별 MatchResults
				Map<Integer, List<Match>> dailyMatchesByDayOfMonth = matchesByMonth.get(month).stream().collect(Collectors.groupingBy(Match::dayOfMonth, TreeMap::new, Collectors.toList()));
				for (int dayOfMonth : dailyMatchesByDayOfMonth.keySet()) {
					monthlyMatchStat.accumulateOrAddDailyStat(DailyMatchStat.of(summoner.getPuuid(), dayOfMonth, dailyMatchesByDayOfMonth.get(dayOfMonth)));
				}

				calculatedMonthlyMatchStats.add(monthlyMatchStat);
			}
		}

		return calculatedMonthlyMatchStats;
	}
}
