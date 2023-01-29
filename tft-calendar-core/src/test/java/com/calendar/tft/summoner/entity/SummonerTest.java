package com.calendar.tft.summoner.entity;

import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SummonerTest {
	@Test
	@DisplayName("최근 갱신 여부: 9분 전에 갱신됐다면 true")
	void recentlyRenewed() {
		// given
		Instant now = Instant.now();
		Instant nineMinAgo = now.minus(9, ChronoUnit.MINUTES); // 9분 전
		Summoner summoner = SummonerFixture.create();
		summoner.updateLastFetchedAt(nineMinAgo);

		// when
		boolean isRecentlyRenewed = summoner.isRecentlyRenewed();

		// then
		assertThat(isRecentlyRenewed).isTrue();
	}

	@Test
	@DisplayName("최근 갱신 여부: 11분 전에 갱신됐다면 false")
	void notRecentlyRenewed() {
		// given
		Instant now = Instant.now();
		Instant elevenMinAgo = now.minus(11, ChronoUnit.MINUTES); // 11분 전
		Summoner summoner = SummonerFixture.create();
		summoner.updateLastFetchedAt(elevenMinAgo);

		// when
		boolean isRecentlyRenewed = summoner.isRecentlyRenewed();

		// then
		assertThat(isRecentlyRenewed).isFalse();
	}
}
