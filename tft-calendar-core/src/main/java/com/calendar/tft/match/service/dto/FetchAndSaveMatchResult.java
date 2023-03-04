package com.calendar.tft.match.service.dto;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import com.calendar.tft.match.domain.entity.Match;

public record FetchAndSaveMatchResult(
	List<String> targetMatchIds,
	List<Match> matches,
	List<Match> existMatches,
	List<Match> fetchedMatches
) {
	public FetchAndSaveMatchResult(List<String> targetMatchIds, List<Match> existMatches, List<Match> fetchedMatches) {
		// 매치 정보들을 플레이 일시 내림차순으로 정렬해서 필드로 설정
		this(
			Objects.requireNonNull(targetMatchIds),
			Stream.concat(existMatches.stream(), fetchedMatches.stream()).toList(),
			Objects.requireNonNull(existMatches).stream().sorted((Comparator.comparing(Match::getPlayedAt).reversed())).toList(),
			Objects.requireNonNull(fetchedMatches).stream().sorted((Comparator.comparing(Match::getPlayedAt).reversed())).toList()
		);
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
