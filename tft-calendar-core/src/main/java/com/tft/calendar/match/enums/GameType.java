package com.tft.calendar.match.enums;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GameType {
	STANDARD(1090, "일반"),
	RANK(1100, "랭크"),
	TUTORIAL(1110, "튜토리얼"),
	TEST(1111, "테스트");

	private final int gameTypeId;
	private final String text;

	private static Map<Integer, GameType> gameTypeById = new HashMap<>();

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
		return gameTypeById.get(gameTypeId);
	}

	public int getGameTypeId() {
		return gameTypeId;
	}

	@JsonValue
	public String getText() {
		return text;
	}
}
