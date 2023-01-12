package com.calendar.tft.match.entity;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.calendar.tft.match.enums.GameType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

@Table("tft_match")
@Getter
@RequiredArgsConstructor
public class Match {
	@Id
	private String id;
	private final String matchId;
	private final GameType gameType;
	private final Instant playedAt;

	@Component
	static class MatchBeforeConvertCallback implements BeforeConvertCallback<Match> {
		@Override
		public Match onBeforeConvert(Match aggregate) {
			String dateString = DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.of("Asia/Seoul")).format(aggregate.playedAt);
			aggregate.id = dateString + aggregate.matchId;

			return aggregate;
		}
	}
}
