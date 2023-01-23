package com.calendar.tft.match.service.dto;

import com.calendar.tft.match.domain.enums.MatchRenewResultStatus;

public record MatchRenewResult(
	String puuid,
	MatchRenewResultStatus resultStatus
) {
	public static MatchRenewResult started(String puuid) {
		return new MatchRenewResult(puuid, MatchRenewResultStatus.STARTED);
	}
	public static MatchRenewResult alreadyProcessing(String puuid) {
		return new MatchRenewResult(puuid, MatchRenewResultStatus.ALREADY_PROCESSING);
	}

	public static MatchRenewResult recentlyRenewed(String puuid) {
		return new MatchRenewResult(puuid, MatchRenewResultStatus.RECENTLY_RENEWED);
	}
}
