package com.calendar.tft.matchStat.service.adapter.dto;

import java.util.List;

import com.calendar.tft.matchStat.entity.MonthlyMatchStat;
import com.calendar.tft.summoner.entity.Summoner;

public record MonthlyMatchStatView(
	String puuid,
	String summonerName,
	int year,
	int month,
	List<DailyMatchStatView> dailyMatchStats
) {
	public static MonthlyMatchStatView from(Summoner summoner, MonthlyMatchStat monthlyMatchStat) {
		List<DailyMatchStatView> dailyMatchStatViews = monthlyMatchStat.getDailyMatchStats().stream().map(dms -> new DailyMatchStatView(
			dms.getDayOfMonth(),
			dms.getPlaytimeInSeconds(),
			dms.getPlayedGameCount(),
			dms.getAveragePlacement())).toList();

		return new MonthlyMatchStatView(
			monthlyMatchStat.getPuuid(),
			summoner.getName(),
			monthlyMatchStat.getYear(),
			monthlyMatchStat.getMonth(),
			dailyMatchStatViews
		);
	}

	record DailyMatchStatView(
		int dayOfMonth,
		int playtimeInSeconds,
		int playedGameCount,
		float averagePlacement
	) {
	}
}
