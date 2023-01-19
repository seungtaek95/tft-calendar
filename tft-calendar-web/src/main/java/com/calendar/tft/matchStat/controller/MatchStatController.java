package com.calendar.tft.matchStat.controller;

import com.calendar.tft.matchStat.service.adapter.MatchStatAdapter;
import com.calendar.tft.matchStat.service.adapter.dto.MonthlyMatchStatView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/match-stats")
@RequiredArgsConstructor
public class MatchStatController {
	private final MatchStatAdapter matchStatAdapter;

	@RequestMapping(value = "/summoner/{summonerName}/monthly", method = RequestMethod.GET)
	public MonthlyMatchStatView getMonthlyMatchStat(
			@PathVariable String summonerName,
			@RequestParam int year,
			@RequestParam int month) {

		return matchStatAdapter.getMonthlyMatchStats(summonerName, year, month);
	}
}
