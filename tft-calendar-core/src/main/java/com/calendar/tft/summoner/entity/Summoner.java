package com.calendar.tft.summoner.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("summoner")
@Getter
@RequiredArgsConstructor
public class Summoner {
	@Id
	private Long id;
	private final String summonerId;
	private final String accountId;
	private final String puuid;
	private final String name;
	private final long profileIconId;
	private final int level;
}
