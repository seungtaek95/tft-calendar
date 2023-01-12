package com.calendar.tft.summoner.service;

import com.calendar.tft.summoner.service.dto.SummonerDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled // 초기 개발시 API 테스트용.
@SpringBootTest
public class SummonerFetcherTest {
	@Autowired
	private SummonerFetcher summonerFetcher;

	@Test
	void fetchMatchIdsByPuuid() {
		SummonerDto result = summonerFetcher.fetchSummonerByPuuid("l_eOUqNT-HoaZCoAOGR20qvxsqJhCtdi1SgtUOAZ3vHAYFcv5--t8AYMyf5PwO5CxgIczDfWbu3m5A");
		System.out.println(result);
	}
}
