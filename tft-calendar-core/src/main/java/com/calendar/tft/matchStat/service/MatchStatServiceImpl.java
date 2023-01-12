package com.calendar.tft.matchStat.service;

import java.util.*;

import com.calendar.tft.matchStat.entity.DailyMatchStat;
import com.calendar.tft.matchStat.entity.MonthlyMatchStat;
import com.calendar.tft.matchStat.repository.MonthlyMatchStatRepository;
import com.calendar.tft.match.entity.MatchRaw;
import com.calendar.tft.match.repository.MatchRawRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchStatServiceImpl implements MatchStatService {
	private final MatchRawRepository matchRawRepository;
	private final MonthlyMatchStatRepository monthlyMatchStatRepository;

	@Override
	public void calculateStatistics(String puuid) {
		Iterable<MatchRaw> matchRaws = matchRawRepository.findAll();

		// 연도별 MatchRaw 들
		Map<Integer, List<MatchRaw>> matchRawsByYear = new HashMap<>();
		for (MatchRaw matchRaw : matchRaws) {
			matchRawsByYear.putIfAbsent(matchRaw.getYear(), new LinkedList<>());
			matchRawsByYear.get(matchRaw.getYear()).add(matchRaw);
		}

		for (int year : matchRawsByYear.keySet()) {
			// 월별 MatchRaw 들
			Map<Integer, List<MatchRaw>> matchRawsByMonth = new HashMap<>();
			for (MatchRaw matchRaw : matchRawsByYear.get(year)) {
				matchRawsByMonth.putIfAbsent(matchRaw.getMonth(), new LinkedList<>());
				matchRawsByMonth.get(matchRaw.getMonth()).add(matchRaw);
			}

			for (int month : matchRawsByMonth.keySet()) {
				MonthlyMatchStat monthlyMatchStat = new MonthlyMatchStat(puuid, year, month);

				// 일별 MatchRaw 들
				Map<Integer, List<MatchRaw>> dailyMatchRawsByDayOfMonth = new HashMap<>();
				for (MatchRaw matchRaw : matchRawsByMonth.get(month)) {
					dailyMatchRawsByDayOfMonth.putIfAbsent(matchRaw.getDayOfMonth(), new LinkedList<>());
					dailyMatchRawsByDayOfMonth.get(matchRaw.getDayOfMonth()).add(matchRaw);
				}

				for (int dayOfMonth : dailyMatchRawsByDayOfMonth.keySet()) {
					monthlyMatchStat.addDailyStat(DailyMatchStat.from(puuid, dayOfMonth, dailyMatchRawsByDayOfMonth.get(dayOfMonth)));
				}

				monthlyMatchStatRepository.save(monthlyMatchStat);
			}
		}
	}
}
