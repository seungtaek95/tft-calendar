package com.calendar.tft.summoner.service.adapter.dto;

import java.time.Instant;

import com.calendar.tft.summoner.entity.Summoner;
import org.springframework.lang.Nullable;

public record SummonerView(
	long id,
	String puuid,
	String name,
	@Nullable
	Instant lastFetchedAt
) {
	public static SummonerView from(Summoner summoner) {
		return new SummonerView(
			summoner.getSummonerNo(),
			summoner.getPuuid(),
			summoner.getName(),
			summoner.getLastFetchedAt());
	}
}
