package com.calendar.tft.summoner.service.dto;

public record SummonerDto(
	String id,
	String accountId,
	String puuid,
	String name,
	String profileIconId,
	long summonerLevel
) {
}
