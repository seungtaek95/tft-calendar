package com.calendar.tft.match.repository;

public interface MatchRenewRepository {
	boolean isPuuidInWaitingQueue(String puuid);
	void addToWaitingQueue(String puuid);
	boolean isPuuidInProcessingQueue(String puuid);
	void addToProcessingQueue(String puuid);
}
