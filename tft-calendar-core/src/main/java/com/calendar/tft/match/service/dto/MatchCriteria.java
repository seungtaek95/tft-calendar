package com.calendar.tft.match.service.dto;

import org.springframework.lang.Nullable;

public record MatchCriteria(
	@Nullable
	Integer startIndex,
	@Nullable
	Long startTimeInSeconds,
	@Nullable
	Long endTimeInSeconds,
	@Nullable
	Integer count
) {
}
