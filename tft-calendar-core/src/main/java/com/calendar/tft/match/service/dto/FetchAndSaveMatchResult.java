package com.calendar.tft.match.service.dto;

import java.time.Instant;
import java.util.List;

import com.calendar.tft.match.domain.entity.Match;

public record FetchAndSaveMatchResult(
	List<String> targetMatchIds,
	List<Match> existMatches,
	List<Match> fetchedMatches
) {
	public Instant oldestMatchPlayedAt() {
		if (existMatches.isEmpty() && fetchedMatches.isEmpty()) {
			throw new RuntimeException("매치 결과가 없습니다.");
		} else if (existMatches.isEmpty()) {
			return fetchedMatches.get(fetchedMatches.size() - 1).getPlayedAt();
		} else if (fetchedMatches.isEmpty()) {
			return existMatches.get(existMatches.size() - 1).getPlayedAt();
		}

		return existMatches.get(existMatches.size() - 1).getPlayedAt().isBefore(fetchedMatches.get(fetchedMatches.size() - 1).getPlayedAt())
			? existMatches.get(existMatches.size() - 1).getPlayedAt()
			: fetchedMatches.get(fetchedMatches.size() - 1).getPlayedAt();
	}
}
