package com.calendar.tft.matchStat.entity;

import java.time.Instant;
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
	private final String puuid;
	private final int year;
	private final int month;
	private final List<DailyMatchStat> dailyMatchStats;
	private Instant lastUpdatedAt;

	public MonthlyMatchStat(String puuid, int year, int month, List<DailyMatchStat> dailyMatchStats) {
		this.puuid = puuid;
		this.year = year;
		this.month = month;
		this.dailyMatchStats = dailyMatchStats;
		this.lastUpdatedAt = Instant.now();
	}

	public void accumulateOrAddDailyStat(DailyMatchStat newDailyMatchStat) {
		for (DailyMatchStat dailyMatchStat : this.getDailyMatchStats()) {
			// 기존 일간 통계가 있는 경우 accumulate 후 return
			if (dailyMatchStat.getDayOfMonth() == newDailyMatchStat.getDayOfMonth()) {
				dailyMatchStat.accumulate(newDailyMatchStat);
				this.updateLastUpdatedAt();
				return;
			}
		}

		this.getDailyMatchStats().add(newDailyMatchStat);
		this.sortDailyMatchStats();
		this.updateLastUpdatedAt();
	}

	private void sortDailyMatchStats() {
		this.dailyMatchStats.sort(Comparator.comparingInt(DailyMatchStat::getDayOfMonth));
	}

	private void updateLastUpdatedAt() {
		this.lastUpdatedAt = Instant.now();
	}
}
