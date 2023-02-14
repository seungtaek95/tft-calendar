package com.calendar.tft.summoner.service.adapter;

import com.calendar.tft.match.service.MatchRenewQueue;
import com.calendar.tft.summoner.repository.SummonerRepository;
import com.calendar.tft.summoner.service.adapter.dto.SummonerView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SummonerAdapter {
	private final SummonerRepository summonerRepository;
	private final MatchRenewQueue matchRenewQueue;

	public SummonerView getSummonerByName(String name) {
		return summonerRepository.findByName(name)
			.map(summoner -> SummonerView.from(summoner, matchRenewQueue.isPuuidInProcessingQueue(summoner.getPuuid())))
			.orElseThrow();
	}
}
