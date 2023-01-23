package com.calendar.tft.summoner.controller.dto;

import com.calendar.tft.match.domain.enums.MatchRenewResultStatus;

public record MatchRenewResultResponse(
	MatchRenewResultStatus matchRenewResultStatus
) {
}
