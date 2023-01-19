package com.calendar.tft.summoner.service;

import com.calendar.tft.summoner.service.dto.SummonerDto;

public interface SummonerFetcher {
	SummonerDto fetchSummonerByPuuid(String puuid);
	SummonerDto fetchSummonerByName(String name);
}
