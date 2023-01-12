package com.calendar.tft.matchStat.service.adapter.dto;

import java.util.List;

import com.calendar.tft.matchStat.entity.MonthlyMatchStat;

public record MonthlyMatchStatView(
	String puuid,
	int year,
	int month,
	List<DailyMatchStatView> dailyMatchStats
) {
	public static MonthlyMatchStatView from(MonthlyMatchStat monthlyMatchStat) {
		List<DailyMatchStatView> dailyMatchStatViews = monthlyMatchStat.getDailyMatchStats().stream().map(dms -> new DailyMatchStatView(
			dms.getDayOfMonth(),
			dms.getPlaytimeInSeconds(),
			dms.getPlayedGameCount(),
			dms.getAveragePlacement())).toList();

		return new MonthlyMatchStatView(
			monthlyMatchStat.getPuuid(),
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
