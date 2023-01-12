package com.tft.calendar.matchStat.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "monthlyStat")
public class MonthlyMatchStat {
	@Id
	private String id;
	private String puuuid;
	private int year;
	private int month;
	private List<DailyMatchStat> dailyMatchStats = new ArrayList<>(31);

	public MonthlyMatchStat(String puuid, int year, int month) {
		this.puuuid = puuid;
		this.year = year;
		this.month = month;
	}

	public void addDailyStat(DailyMatchStat dailyMatchStat) {
		dailyMatchStats.add(dailyMatchStat);
	}
}
