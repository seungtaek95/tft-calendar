package com.calendar.tft.summoner.controller;

import com.calendar.tft.summoner.service.SummonerService;
import com.calendar.tft.summoner.service.adapter.SummonerAdapter;
import com.calendar.tft.summoner.service.adapter.dto.SummonerView;
import com.calendar.tft.summoner.service.dto.SearchSummonerResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/summoners")
@RequiredArgsConstructor
public class SummonerController {
	private final SummonerService summonerService;
	private final SummonerAdapter summonerAdapter;

	@RequestMapping(value = "/{summonerName}", method = RequestMethod.GET)
	public SummonerView getSummonerByName(
			@PathVariable String summonerName) {

		return summonerAdapter.getSummonerByName(summonerName);
	}

	@RequestMapping(value = "/{summonerName}/search", method = RequestMethod.POST)
	public SearchSummonerResult searchSummonerByName(
		@PathVariable String summonerName) {

		return summonerService.searchByName(summonerName);
	}
}
