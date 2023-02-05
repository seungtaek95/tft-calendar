package com.calendar.tft.match.service;

import java.util.List;

import com.calendar.tft.match.domain.entity.Match;
import com.calendar.tft.match.service.dto.MatchCriteria;
import com.calendar.tft.summoner.entity.Summoner;

public interface MatchFetchService {
	/**
	 * 매치 조회 후 저장
	 * @return 저장된 매치 수
	 */
	List<Match> fetchAndSaveMatchData(Summoner summoner, MatchCriteria matchCriteria);
}
