package com.calendar.tft.summoner.service;

import com.calendar.tft.summoner.service.dto.SummonerResponse;

public interface SummonerFetcher {
	SummonerResponse fetchSummonerByPuuid(String puuid);
	SummonerResponse fetchSummonerByName(String name);
}
