package com.calendar.tft.summoner.entity;

public class SummonerFixture {
	public static Summoner create() {
		return new Summoner(
			1L,
			"summonerId",
			"accountId",
			"puuid",
			"name",
			1L,
			10,
			SummonerTftStat.create());
	}

	public static Summoner withoutSummonerNo() {
		return new Summoner(
			null,
			"summonerId",
			"accountId",
			"puuid",
			"name",
			1L,
			10,
			SummonerTftStat.create());
	}
}
