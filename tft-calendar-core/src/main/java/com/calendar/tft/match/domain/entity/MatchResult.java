package com.calendar.tft.match.domain.entity;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("tft_match_result")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchResult {
	@Id
	private String matchResultNo;
	private final String matchNo;
	private final String puuid;
	private final int placement;
	private final int playtimeInSeconds;

	public static MatchResult create(String matchNo, String matchId, String puuid, int placement, int playtimeInSeconds, Instant playedAt) {
		MatchResult matchResult = new MatchResult(
			Objects.requireNonNull(matchNo),
			Objects.requireNonNull(puuid),
			placement,
			playtimeInSeconds);

		matchResult.matchResultNo = matchResult.generateMatchResultNo(playedAt, matchId, placement);

		return matchResult;
	}

	public String generateMatchResultNo(Instant playedAt, String matchId, int placement) {
		return DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.of("Asia/Seoul")).format(playedAt)
			+ matchId
			+ placement;
	}
}
