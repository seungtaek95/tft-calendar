package com.calendar.tft.matchStat.entity;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import com.calendar.tft.match.entity.MatchRaw;
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

	public static DailyMatchStat from(String puuid, int dayOfMonth, Collection<MatchRaw> dailyMatchRaws) {
		AtomicInteger sumPlaytimeInSeconds = new AtomicInteger(0);
		AtomicInteger sumPlayedGameCount = new AtomicInteger(0);
		AtomicInteger sumPlacement = new AtomicInteger(0);

		for (MatchRaw matchRaw : dailyMatchRaws) {
			matchRaw.getParticipantRawByPuuid(puuid)
				.ifPresent(participantRaw -> {
					sumPlaytimeInSeconds.getAndAdd(participantRaw.playtimeInSeconds());
					sumPlayedGameCount.getAndAdd(1);
					sumPlacement.getAndAdd(participantRaw.placement());
				});
		}

		return new DailyMatchStat(
			dayOfMonth,
			sumPlaytimeInSeconds.get(),
			sumPlayedGameCount.get(),
			(float)sumPlacement.get() / (float)sumPlayedGameCount.get());
	}
}
