package com.calendar.tft.summoner.entity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

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
	private Long summonerNo;
	private final String summonerId;
	private final String accountId;
	private final String puuid;
	private String name;
	private long profileIconId;
	private int level;
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
			null);
	}

	public void updateLastFetchedAt(Instant lastFetchedAt) {
		this.lastFetchedAt = lastFetchedAt;
	}

	/**
	 * 최근 갱신 여부
	 * - 마지막 갱신으로부터 10분 이내
	 */
	public boolean isRecentlyRenewed() {
		if (this.getLastFetchedAt().isEmpty()) {
			return false;
		}

		Instant now = Instant.now();
		long minutesAfterLastFetchedAt = ChronoUnit.MINUTES.between(this.getLastFetchedAt().get(), now);

		return minutesAfterLastFetchedAt < 10;
	}

	public Optional<Instant> getLastFetchedAt() {
		return Optional.ofNullable(lastFetchedAt);
	}
}
