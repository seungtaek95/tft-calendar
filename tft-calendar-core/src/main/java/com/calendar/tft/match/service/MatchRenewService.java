package com.calendar.tft.match.service;

import java.util.Optional;

import com.calendar.tft.match.service.dto.MatchRenewResult;
import com.calendar.tft.summoner.entity.Summoner;

public interface MatchRenewService {
	MatchRenewResult tryRenew(Summoner summoner);
	Optional<Summoner> getOldestWaitingSummoner();
	void renew(Summoner summoner);
}
