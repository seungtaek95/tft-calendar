package com.calendar.tft.summoner.service;

import com.calendar.tft.summoner.entity.Summoner;
import com.calendar.tft.summoner.repository.SummonerRepository;
import com.calendar.tft.summoner.service.dto.SummonerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummonerServiceImpl implements SummonerService {
	private final SummonerRepository summonerRepository;
	private final SummonerFetcher summonerFetcher;

	@Override
	public Summoner findAndSaveIfAbsent(String name) {
		return summonerRepository.findByName(name)
			.orElseGet(() -> this.fetchAndSaveByName(name));
	}

	@Override
	public Summoner fetchAndSaveByName(String name) {
		SummonerDto summonerDto = summonerFetcher.fetchSummonerByName(name);
		Summoner summoner = summonerDto.toSummoner();

		summonerRepository.save(summoner);

		return summoner;
	}
}
