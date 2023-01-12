package com.calendar.tft.match.service;

import java.util.List;

import com.calendar.tft.match.service.dto.MatchCriteria;
import com.calendar.tft.match.service.dto.MatchDto;

public interface MatchFetcher {
	List<String> fetchMatchIdsByPuuid(String puuid, MatchCriteria matchCriteria);

	MatchDto fetchMatchById(String matchId);

	int fetchAndSaveMatchRaws(String puuid, long coolTimeMillis) throws InterruptedException;
}
