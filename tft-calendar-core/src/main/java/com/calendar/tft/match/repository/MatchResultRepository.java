package com.calendar.tft.match.repository;

import java.time.Instant;
import java.util.List;

import com.calendar.tft.match.domain.entity.MatchResult;
import com.calendar.tft.match.service.dto.MatchResultOfSummoner;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface MatchResultRepository extends CrudRepository<MatchResult, String> {
	@Query(value =
		"""
		SELECT
			tm.match_id AS match_id, tm.game_type AS game_type, tm.played_at AS played_at,
			tmr.summoner_no AS summoner_no, tmr.placement AS placement, tmr.playtime_in_seconds AS playtime_in_seconds
		FROM
			tft_match_result tmr
		LEFT JOIN
			tft_match tm ON tm.match_no = tmr.match_no
		WHERE
			tm.played_at > :startPlayedAt
		AND
			tmr.summoner_no = :summonerNo
		"""
	)
	List<MatchResultOfSummoner> getMatchResultsBy(Long summonerNo, Instant startPlayedAt);
}
