package com.calendar.tft.match.repository;

import java.util.List;

import com.calendar.tft.match.domain.entity.Match;
import com.calendar.tft.match.domain.entity.MatchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomMatchRepositoryImpl implements CustomMatchRepository {
	private final JdbcTemplate jdbcTemplate;
	final String INSERT_MATCH_IGNORE_DUPLICATE = """
		INSERT INTO tft_match (match_no, match_id, game_type_id, played_at)
		VALUES (?, ?, ?, ?)
		ON DUPLICATE KEY UPDATE match_id = match_id
		""";
	final String INSERT_MATCH_RESULT_IGNORE_DUPLICATE = """
		INSERT INTO tft_match_result (match_result_no, match_no, puuid, placement, playtime_in_seconds)
		VALUES (?, ?, ?, ?, ?)
		ON DUPLICATE KEY UPDATE match_no = match_no
		""";

	@Override
	public Iterable<Match> saveAllIgnoreDuplicate(List<Match> matches) {
		jdbcTemplate.batchUpdate(
			INSERT_MATCH_IGNORE_DUPLICATE,
			matches.stream().map(match -> new Object[]{
				match.getMatchNo(),
				match.getMatchId(),
				match.getGameTypeId(),
				match.getPlayedAt()
			}).toList()
		);

		List<MatchResult> matchResults = matches.stream()
			.flatMap(match -> match.getMatchResultByPuuid().values().stream())
			.toList();

		jdbcTemplate.batchUpdate(
			INSERT_MATCH_RESULT_IGNORE_DUPLICATE,
			matchResults.stream().map(matchResult -> new Object[]{
				matchResult.getMatchResultNo(),
				matchResult.getMatchNo(),
				matchResult.getPuuid(),
				matchResult.getPlacement(),
				matchResult.getPlaytimeInSeconds()
			}).toList()
		);

		return matches;
	}
}
