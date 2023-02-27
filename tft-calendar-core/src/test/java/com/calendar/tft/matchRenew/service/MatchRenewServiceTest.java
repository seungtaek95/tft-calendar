package com.calendar.tft.matchRenew.service;

import com.calendar.tft.match.service.MatchRenewService;
import com.calendar.tft.summoner.entity.Summoner;
import com.calendar.tft.summoner.repository.SummonerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MatchRenewServiceTest {
	@Autowired
	private SummonerRepository summonerRepository;

	@Autowired
	private MatchRenewService sut;

	@Test
	@Transactional
	void renew() throws InterruptedException {
		Summoner summoner = summonerRepository.findByName("쨍 이").orElseThrow();

		sut.manualRenew(summoner);
	}
}
