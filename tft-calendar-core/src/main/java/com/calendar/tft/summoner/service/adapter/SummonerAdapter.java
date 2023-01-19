package com.calendar.tft.summoner.service.adapter;

import com.calendar.tft.summoner.entity.Summoner;
import com.calendar.tft.summoner.service.SummonerService;
import com.calendar.tft.summoner.service.adapter.dto.SummonerView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SummonerAdapter {
	private final SummonerService summonerService;

	public SummonerView getSummonerByName(String name) {
		Summoner summoner = summonerService.findAndSaveIfAbsent(name);
		return SummonerView.from(summoner);
	}
}
