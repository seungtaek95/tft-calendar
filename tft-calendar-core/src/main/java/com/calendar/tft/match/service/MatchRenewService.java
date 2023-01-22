package com.calendar.tft.match.service;

import com.calendar.tft.match.service.dto.MatchRenewResult;
import com.calendar.tft.summoner.entity.Summoner;

public interface MatchRenewService {
	MatchRenewResult renew(Summoner summoner);
}
