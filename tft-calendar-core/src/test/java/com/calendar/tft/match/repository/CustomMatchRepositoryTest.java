package com.calendar.tft.match.repository;

import java.time.Instant;
import java.util.List;
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
		// given
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

		Match match1 = Match.create("matchId1", GameType.RANK.getGameTypeId(), Instant.ofEpochMilli(1000L));
		MatchResult matchResult1 = MatchResult.create(match1.getMatchNo(), match1.getMatchId(), summoner1.getPuuid(), 1, 1000, Instant.now());
		MatchResult matchResult2 = MatchResult.create(match1.getMatchNo(), match1.getMatchId(), summoner2.getPuuid(), 2, 2000, Instant.now());
		match1.setMatchResults(List.of(matchResult1, matchResult2));

		Match match2 = Match.create("matchId1", GameType.RANK.getGameTypeId(), Instant.ofEpochMilli(1000L));
		MatchResult matchResult3 = MatchResult.create(match2.getMatchNo(), match2.getMatchId(), summoner1.getPuuid(), 1, 1000, Instant.now());
		MatchResult matchResult4 = MatchResult.create(match2.getMatchNo(), match2.getMatchId(), summoner2.getPuuid(), 2, 2000, Instant.now());
		match2.setMatchResults(List.of(matchResult3, matchResult4));

		// when
		sut.saveAllIgnoreDuplicate(List.of(match1, match2));

		// then
		Optional<Match> foo = sut.findById(match1.getMatchNo());
		System.out.println(foo.get());
	}
}
