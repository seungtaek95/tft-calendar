package com.tft.calendar.match.entity;

import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "tftMatchRaw")
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
}
