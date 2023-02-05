package com.calendar.tft.match.domain.enums;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GameType {
	STANDARD(1090, "일반"),
	RANK(1100, "랭크"),
	TUTORIAL(1110, "튜토리얼"),
	FORTUNES_FAVOR(1170, "행운의 주인공"),
	ETC(-1, "기타");

	private final int gameTypeId;
	private final String text;

	private final static Map<Integer, GameType> gameTypeById = new HashMap<>();

	static {
		for (GameType gameType : GameType.values()) {
			gameTypeById.put(gameType.gameTypeId, gameType);
		}
	}

	GameType(int gameTypeId, String text) {
		this.gameTypeId = gameTypeId;
		this.text = text;
	}

	public static GameType of(int gameTypeId) {
		return gameTypeById.getOrDefault(gameTypeId, GameType.ETC);
	}

	public int getGameTypeId() {
		return gameTypeId;
	}

	@JsonValue
	public String getText() {
		return text;
	}
}
