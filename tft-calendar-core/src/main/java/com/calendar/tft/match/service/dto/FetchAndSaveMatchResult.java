package com.calendar.tft.match.service.dto;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.calendar.tft.match.domain.entity.Match;

public record FetchAndSaveMatchResult(
	List<String> targetMatchIds,
	List<Match> existMatches,
	List<Match> fetchedMatches
) {
	public FetchAndSaveMatchResult(List<String> targetMatchIds, List<Match> existMatches, List<Match> fetchedMatches) {
		this.targetMatchIds = Objects.requireNonNull(targetMatchIds);
		this.existMatches = Objects.requireNonNull(existMatches);
		this.fetchedMatches = Objects.requireNonNull(fetchedMatches);

		// 매치 정보들을 플레이 일시 내림차순으로 정렬
		if (!existMatches.isEmpty()) {
			existMatches.sort(Comparator.comparing(Match::getPlayedAt).reversed());
		}
		if (!fetchedMatches.isEmpty()) {
			fetchedMatches.sort(Comparator.comparing(Match::getPlayedAt).reversed());
		}
	}

	public Instant getOldestMatchPlayedAt() {
		if (existMatches.isEmpty() && fetchedMatches.isEmpty()) {
			throw new RuntimeException("매치 결과가 없습니다.");
		}
		if (existMatches.isEmpty()) {
			return fetchedMatches.get(fetchedMatches.size() - 1).getPlayedAt();
		}
		if (fetchedMatches.isEmpty()) {
			return existMatches.get(existMatches.size() - 1).getPlayedAt();
		}

		return existMatches.get(existMatches.size() - 1).getPlayedAt().isBefore(fetchedMatches.get(fetchedMatches.size() - 1).getPlayedAt())
			? existMatches.get(existMatches.size() - 1).getPlayedAt()
			: fetchedMatches.get(fetchedMatches.size() - 1).getPlayedAt();
	}
}
