package com.calendar.tft.matchStat.service.adapter;

import java.util.LinkedList;

import com.calendar.tft.matchStat.entity.MonthlyMatchStat;
import com.calendar.tft.matchStat.repository.MonthlyMatchStatRepository;
import com.calendar.tft.matchStat.service.adapter.dto.MonthlyMatchStatView;
import com.calendar.tft.summoner.entity.Summoner;
import com.calendar.tft.summoner.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchStatAdapterImpl implements MatchStatAdapter {
	private final SummonerRepository summonerRepository;
	private final MonthlyMatchStatRepository monthlyMatchStatRepository;

	@Override
	public MonthlyMatchStatView getMonthlyMatchStats(String summonerName, int year, int month) {
		Summoner summoner = summonerRepository.findByName(summonerName)
			.orElseThrow();

		MonthlyMatchStat monthlyMatchStat = monthlyMatchStatRepository.findByPuuidAndYearAndMonth(summoner.getPuuid(), year, month);
		if (monthlyMatchStat == null) {
			return new MonthlyMatchStatView(summoner.getPuuid(), summoner.getName(), year, month, new LinkedList<>());
		}

		return MonthlyMatchStatView.from(summoner, monthlyMatchStat);
	}
}
