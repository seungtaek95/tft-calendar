package com.calendar.tft.matchStat.entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "monthlyMatchStat")
public class MonthlyMatchStat {
	@Id
	private String id;
	private String puuid;
	private int year;
	private int month;
	private List<DailyMatchStat> dailyMatchStats = new ArrayList<>(31);

	public MonthlyMatchStat(String puuid, int year, int month) {
		this.puuid = puuid;
		this.year = year;
		this.month = month;
	}

	public void addDailyStat(DailyMatchStat dailyMatchStat) {
		dailyMatchStats.add(dailyMatchStat);
		dailyMatchStats.sort(Comparator.comparingInt(DailyMatchStat::getDayOfMonth));
	}
}
