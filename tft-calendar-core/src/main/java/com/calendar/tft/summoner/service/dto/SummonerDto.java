package com.calendar.tft.summoner.service.dto;

import com.calendar.tft.summoner.entity.Summoner;

public record SummonerDto(
	String id,
	String accountId,
	String puuid,
	String name,
	long profileIconId,
	int level
) {
	public Summoner toSummoner() {
		return new Summoner(id, accountId, puuid, name, profileIconId, level);
	}
}
