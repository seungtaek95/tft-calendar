package com.calendar.tft.summoner.service;

import com.calendar.tft.summoner.service.adapter.dto.SummonerView;

public interface SummonerService {
	SummonerView searchByName(String name);
}
