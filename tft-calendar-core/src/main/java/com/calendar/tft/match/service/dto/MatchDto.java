package com.calendar.tft.match.service.dto;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.calendar.tft.match.domain.entity.MatchResult;
import com.calendar.tft.summoner.entity.Summoner;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.calendar.tft.match.domain.entity.Match;
import com.calendar.tft.match.domain.enums.GameType;

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
		@JsonProperty("queue_id")
		int gameTypeId, // 게임 유형 ID
		List<ParticipantDto> participants // 게임 참가자 리스트
	) {
	}

	record ParticipantDto(
		String puuid, // 사용자 고유 ID
		int placement, // 순위
		@JsonProperty("time_eliminated")
		float playtimeInSeconds // 플레이타임(seconds)
	) {
	}

	public Match toMatchOf(Summoner summoner) {
		for (ParticipantDto participant : this.info().participants()) {
			if (!Objects.equals(participant.puuid(), summoner.getPuuid())) {
				continue;
			}

			MatchResult matchResult = MatchResult.create(
				this.metadata().matchId(),
				summoner.getSummonerNo(),
				participant.placement(),
				(int)participant.playtimeInSeconds(),
				Instant.ofEpochMilli(this.info().gameDatetimeInMillis()));

			return new Match(
				this.metadata().matchId(),
				GameType.of(this.info().gameTypeId()),
				Instant.ofEpochMilli(this.info().gameDatetimeInMillis()),
				Map.of(summoner.getSummonerNo(), matchResult));
		}

		throw new RuntimeException();
	}
}
