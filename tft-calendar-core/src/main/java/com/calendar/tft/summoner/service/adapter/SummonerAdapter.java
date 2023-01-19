package com.calendar.tft.summoner.service.adapter;

import java.util.Optional;

import com.calendar.tft.summoner.entity.Summoner;
import com.calendar.tft.summoner.repository.SummonerRepository;
import com.calendar.tft.summoner.service.adapter.dto.SummonerView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SummonerAdapter {
	private final SummonerRepository summonerRepository;

	public SummonerView getSummonerByName(String name) {
		Optional<Summoner> summoner = summonerRepository.findByName(name);
		return SummonerView.from(summoner.orElseThrow());
	}
}
