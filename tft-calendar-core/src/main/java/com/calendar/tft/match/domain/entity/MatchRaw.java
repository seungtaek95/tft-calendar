package com.calendar.tft.match.domain.entity;

import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matchRaw")
@Getter
@RequiredArgsConstructor
public class MatchRaw {
	@Id
	private String id;
	private final String matchId;
	private final long gameDatetimeInMillis;
	private final int gameTypeId;
	private final Map<String, ParticipantRaw> participantRawByPuuid;

	public record ParticipantRaw(
		int placement,
		int playtimeInSeconds) {
	}

	public Optional<ParticipantRaw> getParticipantRawByPuuid(String puuid) {
		return Optional.ofNullable(participantRawByPuuid.get(puuid));
	}

	public int getYear() {
		return Instant.ofEpochMilli(gameDatetimeInMillis).atZone(ZoneId.of("Asia/Seoul")).getYear();
	}

	public int getMonth() {
		return Instant.ofEpochMilli(gameDatetimeInMillis).atZone(ZoneId.of("Asia/Seoul")).getMonthValue();
	}


	public int getDayOfMonth() {
		return Instant.ofEpochMilli(gameDatetimeInMillis).atZone(ZoneId.of("Asia/Seoul")).getDayOfMonth();
	}
}
