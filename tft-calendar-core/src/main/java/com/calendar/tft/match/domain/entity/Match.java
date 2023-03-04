package com.calendar.tft.match.domain.entity;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.calendar.tft.match.domain.enums.GameType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

@Table("tft_match")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Match {
	@Id
	private String matchNo;
	private final String matchId;
	private final int gameTypeId;
	private final Instant playedAt;
	@MappedCollection(idColumn = "match_no", keyColumn = "puuid")
	private Map<String, MatchResult> matchResultByPuuid = new HashMap<>();

	@Component
	private static class MatchBeforeConvertCallback implements BeforeConvertCallback<Match> {
		@Override
		public Match onBeforeConvert(Match aggregate) {
			aggregate.matchNo = aggregate.createMatchNo();

			return aggregate;
		}
	}

	public static Match create(String matchId, int gameTypeId, Instant playedAt) {
		Match match = new Match(
			Objects.requireNonNull(matchId),
			gameTypeId,
			Objects.requireNonNull(playedAt));

		match.matchNo = match.createMatchNo();

		return match;
	}

	private String createMatchNo() {
		return DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.of("Asia/Seoul")).format(this.getPlayedAt())
			+ this.getMatchId();
	}

	public void setMatchResults(List<MatchResult> matchResults) {
		matchResultByPuuid = matchResults.stream()
			.collect(Collectors.toUnmodifiableMap(MatchResult::getPuuid, Function.identity()));
	}

	public int year() {
		return playedAt.atZone(ZoneId.of("Asia/Seoul")).getYear();
	}

	public int month() {
		return playedAt.atZone(ZoneId.of("Asia/Seoul")).getMonthValue();
	}

	public int dayOfMonth() {
		return playedAt.atZone(ZoneId.of("Asia/Seoul")).getDayOfMonth();
	}

	public GameType getGameType() {
		return GameType.of(this.gameTypeId);
	}
}
