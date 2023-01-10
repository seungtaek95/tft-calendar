package com.tft.calendar.match.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GameType {
	STANDARD(1090, "일반"),
	RANK(1100, "랭크"),
	TUTORIAL(1110, "튜토리얼"),
	TEST(1111, "테스트");

	private final int queueId;
	private final String text;

	GameType(int queueId, String text) {
		this.queueId = queueId;
		this.text = text;
	}

	@JsonValue
	public int getQueueId() {
		return queueId;
	}

	public String getText() {
		return text;
	}
}
