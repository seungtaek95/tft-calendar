package com.calendar.tft.summoner.service;

import com.calendar.tft.summoner.service.dto.SearchSummonerResult;

public interface SummonerService {
	SearchSummonerResult searchByName(String name);
}
