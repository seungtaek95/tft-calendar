package com.tft.calendar.summoner.service.dto;

public record SummonerDto(
	String id,
	String accountId,
	String puuid,
	String name,
	String profileIconId,
	long summonerLevel
) {
}
