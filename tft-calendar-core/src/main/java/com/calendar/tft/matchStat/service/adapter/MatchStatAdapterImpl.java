package com.calendar.tft.matchStat.service.adapter;

import java.util.LinkedList;

import com.calendar.tft.matchStat.entity.MonthlyMatchStat;
import com.calendar.tft.matchStat.repository.MonthlyMatchStatRepository;
import com.calendar.tft.matchStat.service.adapter.dto.MonthlyMatchStatView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchStatAdapterImpl implements MatchStatAdapter {
	private final MonthlyMatchStatRepository monthlyMatchStatRepository;

	@Override
	public MonthlyMatchStatView getMonthlyMatchStats(String summonerName, int year, int month) {
		MonthlyMatchStat monthlyMatchStat = monthlyMatchStatRepository.findByPuuidAndYearAndMonth(summonerName, year, month);
		if (monthlyMatchStat == null) {
			return new MonthlyMatchStatView(summonerName, year, month, new LinkedList<>());
		}

		return MonthlyMatchStatView.from(monthlyMatchStat);
	}
}
