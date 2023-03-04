package com.calendar.tft.match.service.dto;

import java.time.Instant;
import java.util.*;

import com.calendar.tft.match.domain.entity.MatchResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.calendar.tft.match.domain.entity.Match;

public record MatchDto(
	MetadataDto metadata,
	InfoDto info
) {
	record MetadataDto(
		@JsonProperty("match_id")
		String matchId // 게임 ID
	) {
	}

	public record InfoDto(
		@JsonProperty("game_datetime")
		long gameDatetimeInMillis, // 게임 일시(millis)
		@JsonProperty("queue_id")
		int gameTypeId, // 게임 유형 ID
		List<ParticipantDto> participants // 게임 참가자 리스트
	) {
	}

	public record ParticipantDto(
		String puuid, // 사용자 고유 ID
		int placement, // 순위
		@JsonProperty("time_eliminated")
		float playtimeInSeconds // 플레이타임(seconds)
	) {
	}
	public Match toMatch() {
		Match match = Match.create(
			this.metadata().matchId(),
			this.info().gameTypeId(),
			Instant.ofEpochMilli(this.info().gameDatetimeInMillis()));

		List<MatchResult> matchResults = this.info().participants().stream()
			.map(participantDto -> MatchResult.create(
				match.getMatchNo(),
				this.metadata().matchId(),
				participantDto.puuid(),
				participantDto.placement(),
				(int) participantDto.playtimeInSeconds(),
				Instant.ofEpochMilli(this.info().gameDatetimeInMillis())))
			.toList();

		match.setMatchResults(matchResults);

		return match;
	}
}
