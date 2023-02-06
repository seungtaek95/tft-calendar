package com.calendar.tft.match.service;

import com.calendar.tft.match.service.dto.FetchAndSaveMatchResult;
import com.calendar.tft.match.service.dto.MatchCriteria;
import com.calendar.tft.summoner.entity.Summoner;

public interface MatchFetchService {
	/**
	 * 매치 조회 후 저장
	 */
	FetchAndSaveMatchResult fetchAndSaveMatchData(Summoner summoner, MatchCriteria matchCriteria);
}
