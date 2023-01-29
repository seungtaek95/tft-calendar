package com.calendar.tft.match.service;

import java.time.Instant;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MatchRenewQueueImpl implements MatchRenewQueue {
	private final RedisTemplate<String, Object> redisTemplate;
	private final String WAITING_QUEUE = "MATCH_RENEW_WAITING_QUEUE";
	private final String PROCESSING_QUEUE = "MATCH_RENEW_PROCESSING_QUEUE";

	@Override
	public boolean isPuuidInWaitingQueue(String puuid) {
		Double score = redisTemplate.opsForZSet().score(WAITING_QUEUE, puuid);
		return score != null && score > 0;
	}

	@Override
	public void addToWaitingQueue(String puuid) {
		redisTemplate.opsForZSet().add(WAITING_QUEUE, puuid, Instant.now().getEpochSecond());
	}

	@Override
	public boolean isPuuidInProcessingQueue(String puuid) {
		Double score = redisTemplate.opsForZSet().score(PROCESSING_QUEUE, puuid);
		return score != null && score > 0;
	}

	@Override
	public void addToProcessingQueue(String puuid) {
		redisTemplate.opsForZSet().add(PROCESSING_QUEUE, puuid, Instant.now().getEpochSecond());
	}

	@Override
	public Optional<String> getOldestPuuidFromWaitingQueue() {
		TypedTuple<Object> data = redisTemplate.opsForZSet().popMax(WAITING_QUEUE);

		if (data == null || data.getValue() == null) {
			return Optional.empty();
		} else {
			return Optional.of((String)data.getValue());
		}
	}

	@Override
	public void removeFromProcessingQueue(String puuid) {
		redisTemplate.opsForZSet().remove(PROCESSING_QUEUE, puuid);
	}
}
