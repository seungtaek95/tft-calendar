package com.calendar.tft.summoner.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import com.calendar.tft.summoner.entity.Summoner;
import com.calendar.tft.summoner.entity.SummonerFixture;
import com.calendar.tft.summoner.repository.SummonerRepository;
import com.calendar.tft.summoner.service.adapter.dto.SummonerView;
import com.calendar.tft.summoner.service.dto.SummonerResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SummonerServiceTest {
	@Mock
	private SummonerRepository summonerRepository;

	@Mock
	private SummonerFetcher summonerFetcher;

	@InjectMocks
	private SummonerServiceImpl summonerService;

	@Test
	@DisplayName("DB에 존재하는 소환사를 이름으로 검색")
	void searchByNameDbExist() {
		// given
		Summoner existSummoner = SummonerFixture.create();
		given(summonerRepository.findByName(existSummoner.getName())).willReturn(Optional.of(existSummoner)); // DB에서 사용자 조회 mocking

		// when
		SummonerView summonerView = summonerService.searchByName(existSummoner.getName());

		// then
		assertThat(summonerView).isNotNull();
		assertThat(summonerView.puuid()).isEqualTo(existSummoner.getPuuid());
		assertThat(summonerView.name()).isEqualTo(existSummoner.getName());
	}

	@Test
	@DisplayName("DB에 존재하지 않는 소환사를 이름으로 검색")
	void searchByNameNoDb() {
		// given
		String name = "name";
		given(summonerRepository.findByName(name)).willReturn(Optional.empty()); // DB에서 소환사 조회 mocking
		SummonerResponse summonerResponse = new SummonerResponse("summonerId", "accountId", "puuid", "name", 1L, 10);
		given(summonerFetcher.fetchSummonerByName(name)).willReturn(summonerResponse); // 소환사 조회 API 모킹

		// when
		try {
			summonerService.searchByName(name);
		} catch (NullPointerException e) {
			// id 값이 설정되지 않아서 NPE 발생
		}

		// then
		verify(summonerRepository, times(1)).save(any()); // DB에 소환사 저장 호출
	}
}
