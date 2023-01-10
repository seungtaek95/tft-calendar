package com.tft.calendar.match.service;

import java.util.List;

import com.tft.calendar.match.service.dto.MatchCriteria;
import com.tft.calendar.match.service.dto.MatchDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled // 초기 개발시 API 테스트용.
@SpringBootTest
public class MatchFetcherTest {
	@Autowired
	private MatchFetcher matchFetcher;

	@Test
	void fetchMatchIdsByPuuid() {
		MatchCriteria matchCriteria = new MatchCriteria(0, null, null, 20);
		List<String> result = matchFetcher.fetchMatchIdsByPuuid("l_eOUqNT-HoaZCoAOGR20qvxsqJhCtdi1SgtUOAZ3vHAYFcv5--t8AYMyf5PwO5CxgIczDfWbu3m5A", matchCriteria);
		System.out.println(result);
	}

	@Test
	void fetchMatchById() {
		MatchDto matchDto = matchFetcher.fetchMatchById("KR_6297719476");
		System.out.println(matchDto);
	}
}
