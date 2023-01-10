package com.tft.calendar.match.service;

import java.util.List;

import com.tft.calendar.match.service.dto.MatchCriteria;
import com.tft.calendar.match.service.dto.MatchDto;

public interface MatchFetcher {
	List<String> fetchMatchIdsByPuuid(String puuid, MatchCriteria matchCriteria);

	MatchDto fetchMatchById(String matchId);
}
