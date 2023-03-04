package com.calendar.tft.summoner.service.dto;

import com.calendar.tft.summoner.entity.Summoner;
import com.fasterxml.jackson.annotation.JsonProperty;

public record SummonerResponse(
	String id,
	String accountId,
	String puuid,
	String name,
	long profileIconId,
	@JsonProperty("summonerLevel")
	int level
) {
	public Summoner toSummoner() {
		return Summoner.create(id, accountId, puuid, name, profileIconId, level);
	}
}
