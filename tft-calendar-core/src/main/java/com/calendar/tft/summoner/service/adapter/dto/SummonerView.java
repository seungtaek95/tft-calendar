package com.calendar.tft.summoner.service.adapter.dto;

import java.time.Instant;

import com.calendar.tft.summoner.entity.Summoner;
import org.springframework.lang.Nullable;

public record SummonerView(
	long summonerNo,
	String puuid,
	String name,
	long profileIconId,
	@Nullable
	Instant lastManualRenewedAt,
	boolean isRenewProcessing
) {
	public static SummonerView from(Summoner summoner, boolean isRenewProcessing) {
		return new SummonerView(
			summoner.getSummonerNo(),
			summoner.getPuuid(),
			summoner.getName(),
			summoner.getProfileIconId(),
			summoner.getSummonerTftStat().getLastManualRenewedAt().orElse(null),
			isRenewProcessing);
	}
}
