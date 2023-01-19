package com.calendar.tft.summoner.controller;

import com.calendar.tft.summoner.service.adapter.SummonerAdapter;
import com.calendar.tft.summoner.service.adapter.dto.SummonerView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/summoners")
@RequiredArgsConstructor
public class SummonerController {
	private final SummonerAdapter summonerAdapter;

	@RequestMapping(value = "/{summonerName}", method = RequestMethod.GET)
	public SummonerView getSummonerByName(
			@PathVariable String summonerName) {

		return summonerAdapter.getSummonerByName(summonerName);
	}
}
