package com.tft.calendar.match.service.dto;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tft.calendar.match.enums.GameType;

public record MatchDto(
	MetadataDto metadata,
	InfoDto info
) {
	record MetadataDto(
		@JsonProperty("match_id")
		String matchId // 게임 ID
	) {
	}

	record InfoDto(
		@JsonProperty("game_datetime")
		long gameDatetimeInMillis, // 게임 일시(millis)
		@JsonProperty("game_length")
		float gameLengthInSeconds, // 게임 길이(seconds)
		@JsonProperty("queue_id")
		GameType gameType, // 게임 유형
		List<ParticipantDto> participants // 게임 참가자 리스트
	) {
		public ZonedDateTime gameDateTime() {
			return Instant.ofEpochMilli(gameDatetimeInMillis).atZone(ZoneId.of("Asia/Seoul"));
		}
	}

	record ParticipantDto(
		String puuid, // 사용자 고유 ID
		int placement // 순위
	) {
	}
}
