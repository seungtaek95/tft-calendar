package com.calendar.tft.summoner.service.adapter.dto;

import com.calendar.tft.summoner.entity.Summoner;

public record SummonerView(
	long id,
	String puuid,
	String name
) {
	public static SummonerView from(Summoner summoner) {
		return new SummonerView(
			summoner.getId(),
			summoner.getPuuid(),
			summoner.getName());
	}
}
