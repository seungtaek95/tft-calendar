package com.calendar.tft.summoner.service.dto;

import com.calendar.tft.summoner.service.adapter.dto.SummonerView;

public record SearchSummonerResult(
	boolean isNew,
	SummonerView summonerView
) {
}
