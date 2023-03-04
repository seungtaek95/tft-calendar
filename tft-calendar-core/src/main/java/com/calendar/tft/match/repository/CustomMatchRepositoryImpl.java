package com.calendar.tft.match.repository;

import java.lang.reflect.Field;
import java.util.List;

import com.calendar.tft.match.domain.entity.Match;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomMatchRepositoryImpl implements CustomMatchRepository {
	private final JdbcTemplate jdbcTemplate;
	final String INSERT_IGNORE_DUPLICATE = """
		INSERT INTO tft_match (match_no, match_id, game_type_id, played_at)
		VALUES (?, ?, ?, ?)
		ON DUPLICATE KEY UPDATE match_id = match_id
		""";

	@Override
	public Iterable<Match> saveAllIgnoreDuplicate(List<Match> matches) {
		jdbcTemplate.batchUpdate(
			INSERT_IGNORE_DUPLICATE,
			matches.stream().map(match -> new Object[] {
				match.createMatchNo(),
				match.getMatchId(),
				match.getGameTypeId(),
				match.getPlayedAt()
			}).toList()
		);

		matches.forEach(match -> {
			try {
				Field matchNoField = match.getClass().getDeclaredField("matchNo");
				matchNoField.setAccessible(true);
				matchNoField.set(match, match.createMatchNo());
				matchNoField.setAccessible(false);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		return matches;
	}
}
