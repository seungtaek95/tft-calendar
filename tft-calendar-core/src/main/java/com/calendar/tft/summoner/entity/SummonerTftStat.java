package com.calendar.tft.summoner.entity;

import java.time.Instant;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Table;

@Table("summoner_tft_stat")
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SummonerTftStat {
	private String lastRenewedMatchId;
	private Instant lastManualRenewedAt;

	public static SummonerTftStat create() {
		return new SummonerTftStat(null, null);
	}

	public Optional<Instant> getLastManualRenewedAt() {
		return Optional.ofNullable(lastManualRenewedAt);
	}

	public void updateLastRenewedMatchId(String lastRenewedMatchId) {
		this.lastRenewedMatchId = lastRenewedMatchId;
	}

	public void updateLastManualRenewedAt(Instant lastManualRenewedAt) {
		this.lastManualRenewedAt = lastManualRenewedAt;
	}
}
