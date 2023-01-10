package com.tft.calendar.match.service.dto;

public record MatchCriteria(
	Integer startIndex,
	Long startTimeInSeconds,
	Long endTimeInSeconds,
	Integer count
) {
}
