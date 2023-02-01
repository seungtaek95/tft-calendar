package com.calendar.tft.match.service.dto;

import java.time.Instant;
import java.time.ZoneId;

import com.calendar.tft.match.domain.enums.GameType;

public record MatchResultOfSummoner(
	long summonerNo,
	int placement,
	int playtimeInSeconds,
	String matchId,
	GameType gameType,
	Instant playedAt
) {

	public int year() {
		return playedAt.atZone(ZoneId.of("Asia/Seoul")).getYear();
	}

	public int month() {
		return playedAt.atZone(ZoneId.of("Asia/Seoul")).getMonthValue();
	}


	public int dayOfMonth() {
		return playedAt.atZone(ZoneId.of("Asia/Seoul")).getDayOfMonth();
	}
}
