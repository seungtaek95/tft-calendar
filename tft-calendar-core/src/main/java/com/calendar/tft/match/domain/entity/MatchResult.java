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

	public static MatchResult create(String matchId, long summonerNo, int position, int playtimeInSeconds, Instant playedAt) {
		String matchResultNo = DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.of("Asia/Seoul")).format(playedAt)
			+ matchId
			+ summonerNo;

		return new MatchResult(
			matchResultNo,
			summonerNo,
			position,
			playtimeInSeconds);
	}
}
