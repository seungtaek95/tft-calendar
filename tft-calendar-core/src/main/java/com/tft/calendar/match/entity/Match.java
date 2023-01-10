package com.tft.calendar.match.entity;

import java.time.Instant;

import com.tft.calendar.match.enums.GameType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("tft_match")
@Getter
@RequiredArgsConstructor
public class Match {
	private final String id;
	private final String matchId;
	private final GameType gameType;
	private final int playtimeInSeconds;
	private final Instant playedAt;
}
