package com.calendar.tft.match.service;

import java.util.List;

import com.calendar.tft.match.service.dto.MatchCriteria;
import com.calendar.tft.match.service.dto.MatchDto;
import com.calendar.tft.summoner.entity.Summoner;

public interface MatchFetcher {
	List<String> fetchMatchIdsByPuuid(String puuid, MatchCriteria matchCriteria);

	MatchDto fetchMatchById(String matchId);

	void fetchAndSaveMatchRaws(Summoner summoner) throws InterruptedException;
}
