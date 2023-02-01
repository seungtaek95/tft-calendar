package com.calendar.tft.matchStat.entity;

import java.util.Collection;

import com.calendar.tft.match.service.dto.MatchResultOfSummoner;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyMatchStat {
	private int dayOfMonth;
	private int playtimeInSeconds;
	private int playedGameCount;
	private float averagePlacement;

	public static DailyMatchStat from(int dayOfMonth, Collection<MatchResultOfSummoner> dailyMatchResults) {
		int sumPlaytimeInSeconds = 0;
		int sumPlayedGameCount = 0;
		int sumPlacement = 0;

		for (MatchResultOfSummoner matchResult : dailyMatchResults) {
			sumPlaytimeInSeconds += matchResult.playtimeInSeconds();
			sumPlayedGameCount += 1;
			sumPlacement += matchResult.placement();
		}

		return new DailyMatchStat(
			dayOfMonth,
			sumPlaytimeInSeconds,
			sumPlayedGameCount,
			(float)sumPlacement / (float)sumPlayedGameCount);
	}

	public void accumulate(DailyMatchStat dailyMatchStat) {
		if (this.dayOfMonth != dailyMatchStat.getDayOfMonth()) {
			throw new IllegalArgumentException("더하려는 통계의 날짜가 다릅니다");
		}

		this.averagePlacement = ((this.playedGameCount * this.averagePlacement) + (dailyMatchStat.getPlayedGameCount() * dailyMatchStat.getAveragePlacement()))
			/ (this.playedGameCount + dailyMatchStat.getPlayedGameCount());
		this.playtimeInSeconds += dailyMatchStat.getPlaytimeInSeconds();
		this.playedGameCount += dailyMatchStat.getPlayedGameCount();
	}
}
