package com.calendar.tft.match.domain.entity;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@RequiredArgsConstructor
@Table("tft_match_result")
public class MatchResult {
	@Id
	private final String matchResultNo;
	private final long summonerNo;
	private final int position;
	private final int playtimeInSeconds;

	public static MatchResult create(long summonerNo, int position, int playtimeInSeconds, Instant playedAt) {
		return new MatchResult(
			DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.of("Asia/Seoul")).format(playedAt) + summonerNo,
			summonerNo,
			position,
			playtimeInSeconds);
	}
}
