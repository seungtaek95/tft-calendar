package com.calendar.tft.summoner.service;

import java.util.Optional;

import com.calendar.tft.match.service.MatchRenewService;
import com.calendar.tft.match.service.dto.MatchRenewResult;
import com.calendar.tft.summoner.entity.Summoner;
import com.calendar.tft.summoner.repository.SummonerRepository;
import com.calendar.tft.summoner.service.adapter.dto.SummonerView;
import com.calendar.tft.summoner.service.dto.SummonerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummonerServiceImpl implements SummonerService {
	private final SummonerRepository summonerRepository;
	private final SummonerFetcher summonerFetcher;
	private final MatchRenewService matchRenewService;

	@Override
	public SummonerView searchByName(String name) {
		// 소환사정보가 DB에 있으면 바로 반환
		Optional<Summoner> summoner = summonerRepository.findByName(name);
		if (summoner.isPresent()) {
			return SummonerView.from(summoner.get());
		}

		// 소환사 정보 가져와서 저장
		Summoner newSummoner = fetchAndSaveByName(name);

		return SummonerView.from(newSummoner);
	}

	@Override
	public MatchRenewResult renewByName(String name) {
		Optional<Summoner> summoner = summonerRepository.findByName(name);
		if (summoner.isEmpty()) {
			throw new RuntimeException();
		}

		return matchRenewService.tryRenew(summoner.get());
	}

	private Summoner fetchAndSaveByName(String name) {
		SummonerResponse summonerResponse = summonerFetcher.fetchSummonerByName(name);
		Summoner summoner = summonerResponse.toSummoner();

		summonerRepository.save(summoner);

		return summoner;
	}
}
