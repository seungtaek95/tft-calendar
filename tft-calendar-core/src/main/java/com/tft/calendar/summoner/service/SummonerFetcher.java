package com.tft.calendar.summoner.service;

import com.tft.calendar.summoner.service.dto.SummonerDto;

public interface SummonerFetcher {
	SummonerDto fetchSummonerByPuuid(String puuid);
}
