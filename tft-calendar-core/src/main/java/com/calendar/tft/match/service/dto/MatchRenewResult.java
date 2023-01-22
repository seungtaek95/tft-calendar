package com.calendar.tft.match.service.dto;

import com.calendar.tft.match.domain.enums.MatchRenewResultStatus;

public record MatchRenewResult(
	String puuid,
	MatchRenewResultStatus resultStatus
) {
}
