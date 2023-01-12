package com.tft.calendar.matchStat.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tft.calendar.matchStat.entity.DailyMatchStat;
import com.tft.calendar.matchStat.entity.MonthlyMatchStat;
import com.tft.calendar.matchStat.repository.MonthlyStatRepository;
import com.tft.calendar.match.entity.MatchRaw;
import com.tft.calendar.match.repository.MatchRawRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchStatServiceImpl implements MatchStatService {
	private final MatchRawRepository matchRawRepository;
	private final MonthlyStatRepository monthlyStatRepository;

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

				monthlyStatRepository.save(monthlyMatchStat);
			}
		}
	}
}
