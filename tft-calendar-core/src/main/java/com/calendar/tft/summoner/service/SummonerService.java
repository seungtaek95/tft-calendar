package com.calendar.tft.summoner.service;

import com.calendar.tft.summoner.entity.Summoner;

public interface SummonerService {
	Summoner findAndSaveIfAbsent(String summonerName);
	Summoner fetchAndSaveByName(String name);
}
