package com.calendar.tft.matchStat.service.adapter;

import com.calendar.tft.matchStat.service.adapter.dto.MonthlyMatchStatView;

public interface MatchStatAdapter {
	MonthlyMatchStatView getMonthlyMatchStats(String summonerName, int year, int month);
}
