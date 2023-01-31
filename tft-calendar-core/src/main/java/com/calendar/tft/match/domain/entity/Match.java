package com.calendar.tft.match.domain.entity;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.calendar.tft.match.domain.enums.GameType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

@Table("tft_match")
@Getter
@RequiredArgsConstructor
public class Match {
	@Id
	private String matchNo;
	private final String matchId;
	private final GameType gameType;
	private final Instant playedAt;
	@MappedCollection(idColumn = "match_no", keyColumn = "summoner_no")
	private final Map<Long, MatchResult> matchResultBySummonerNo;

	@Component
	private static class MatchBeforeConvertCallback implements BeforeConvertCallback<Match> {
		@Override
		public Match onBeforeConvert(Match aggregate) {
			String dateString = DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.of("Asia/Seoul")).format(aggregate.playedAt);
			aggregate.matchNo = dateString + aggregate.matchId;

			return aggregate;
		}
	}
}
