package com.calendar.tft.match.service;

import java.util.List;

import com.calendar.tft.match.service.dto.MatchCriteria;
import com.calendar.tft.match.service.dto.MatchDto;
import reactor.core.publisher.Mono;

public interface MatchFetcher {
	Mono<List<String>> fetchMatchIdsByPuuid(String puuid, MatchCriteria matchCriteria);

	Mono<MatchDto> fetchMatchById(String matchId);
}
