package com.calendar.tft.match.service;

import java.util.Optional;

public interface MatchRenewQueue {
	boolean isPuuidInWaitingQueue(String puuid);
	void addToWaitingQueue(String puuid);
	boolean isPuuidInProcessingQueue(String puuid);
	void addToProcessingQueue(String puuid);
	Optional<String> getOldestPuuidFromWaitingQueue();
	void removeFromProcessingQueue(String puuid);
}
