package com.tft.calendar.summoner.entity;

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
	private final String playerId;
	private final String accountId;
	private final String puuid;
	private final String name;
	private final String profileIconId;
}
