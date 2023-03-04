package com.calendar.tft.matchStat.service;

import java.util.List;

import com.calendar.tft.match.domain.entity.Match;
import com.calendar.tft.summoner.entity.Summoner;

public interface MatchStatService {
	void renewStatistics(Summoner summoner, List<Match> matches);
}
