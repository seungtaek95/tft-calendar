package com.calendar.tft.summoner.entity;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

@Table("summoner")
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Summoner {
	@Id
	private Long id;
	private final String summonerId;
	private final String accountId;
	private final String puuid;
	private String name;
	private long profileIconId;
	private int level;
	@Nullable
	private String lastFetchedMatchId;
	@Nullable
	private Instant lastFetchedAt;

	public static Summoner create(
			String summonerId,
			String accountId,
			String puuid,
			String name,
			long profileIconId,
			int level) {
		return new Summoner(
			null,
			summonerId,
			accountId,
			puuid,
			name,
			profileIconId,
			level,
			null,
			null);
	}

	public void updateLastFetched(String lastFetchedMatchId, Instant lastFetchedAt) {
		this.lastFetchedMatchId = lastFetchedMatchId;
		this.lastFetchedAt = lastFetchedAt;
	}
}
