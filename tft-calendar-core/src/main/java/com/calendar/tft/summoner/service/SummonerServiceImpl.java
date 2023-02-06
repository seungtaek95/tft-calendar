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
		// 소환사정보가 DB에 있으면 바로 반환, 없다면 소환사 정보 가져와서 저장 후 반환
		return summonerRepository.findByName(name)
			.map(SummonerView::from)
			.orElseGet(() -> SummonerView.from(this.fetchAndSaveByName(name)));

	}

	@Override
	public MatchRenewResult renewBySummonerNo(long summonerNo) {
		Optional<Summoner> summoner = summonerRepository.findBySummonerNo(summonerNo);
		if (summoner.isEmpty()) {
			throw new RuntimeException();
		}

		return matchRenewService.tryRenew(summoner.get());
	}

	private Summoner fetchAndSaveByName(String name) {
		// 소환사 정보 조회
		SummonerResponse summonerResponse = summonerFetcher.fetchSummonerByName(name);
		Summoner fetchedSummoner = summonerResponse.toSummoner();

		// 같은 puuid로 저장된 소환사가 있으면 업데이트 후 return
		Optional<Summoner> existSummoner = summonerRepository.findByPuuid(fetchedSummoner.getPuuid());
		if (existSummoner.isPresent()) {
			existSummoner.get().updateProfile(fetchedSummoner);
			summonerRepository.save(existSummoner.get());
			return existSummoner.get();
		}

		summonerRepository.save(fetchedSummoner);
		return fetchedSummoner;
	}
}
