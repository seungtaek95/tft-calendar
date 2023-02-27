package com.calendar.tft.summoner.entity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

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
	@Column("summoner_no")
	private SummonerTftStat summonerTftStat;

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
			SummonerTftStat.create());
	}

	public void updateProfile(Summoner summoner) {
		if (!Objects.equals(this.getPuuid(), summoner.getPuuid())) {
			throw new RuntimeException("소환사가 정보가 다릅니다.");
		}

		this.name = summoner.getName();
		this.profileIconId = summoner.getProfileIconId();
		this.level = summoner.getLevel();
	}

	/**
	 * 최근 갱신 여부
	 * - 마지막 갱신으로부터 10분 이내
	 */
	public boolean isRecentlyRenewed() {
		if (this.getSummonerTftStat().getLastManualRenewedAt().isEmpty()) {
			return false;
		}

		Instant now = Instant.now();
		long minutesAfterLastFetchedAt = ChronoUnit.MINUTES.between(this.getSummonerTftStat().getLastManualRenewedAt().get(), now);

		return minutesAfterLastFetchedAt < 10;
	}
}
