package com.calendar.tft.match.repository;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.calendar.tft.match.domain.entity.Match;
import com.calendar.tft.match.domain.entity.MatchResult;
import com.calendar.tft.match.domain.enums.GameType;
import com.calendar.tft.summoner.entity.Summoner;
import com.calendar.tft.summoner.repository.SummonerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CustomMatchRepositoryTest {
	@Autowired
	private SummonerRepository summonerRepository;

	@Autowired
	private MatchRepository sut;

	@Test
	@Transactional
	void foo() {
		Summoner summoner1 = Summoner.create(
			"summonerId1",
			"accountId1",
			"puuid1",
			"name1",
			1L,
			10);
		Summoner summoner2 = Summoner.create(
			"summonerId2",
			"accountId2",
			"puuid2",
			"name2",
			1L,
			10);
		summonerRepository.saveAll(List.of(summoner1, summoner2));

		MatchResult matchResult1 = MatchResult.create("matchId1", summoner1.getSummonerNo(), 1, 1000, Instant.now());
		MatchResult matchResult2 = MatchResult.create("matchId1", summoner2.getSummonerNo(), 2, 2000, Instant.now());
		Match match = new Match("matchId1", GameType.RANK.getGameTypeId(), Instant.ofEpochMilli(1000L), Map.of(summoner1.getSummonerNo(), matchResult1, summoner2.getSummonerNo(), matchResult2));

		Iterable<Match> matches = sut.saveAllIgnoreDuplicate(List.of(match));

		MatchResult matchResult11 = MatchResult.create("matchId1", summoner1.getSummonerNo(), 1, 1000, Instant.now());
		MatchResult matchResult22 = MatchResult.create("matchId1", summoner2.getSummonerNo(), 2, 2000, Instant.now());
		Match match11 = new Match("matchId1", GameType.RANK.getGameTypeId(), Instant.ofEpochMilli(1000L), Map.of(summoner1.getSummonerNo(), matchResult11, summoner2.getSummonerNo(), matchResult22));

		Iterable<Match> matches1 = sut.saveAllIgnoreDuplicate(List.of(match11));

		Optional<Match> foo = sut.findById(match.getMatchNo());
	}
}
