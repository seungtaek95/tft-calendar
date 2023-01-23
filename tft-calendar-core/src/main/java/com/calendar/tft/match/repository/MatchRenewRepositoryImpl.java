package com.calendar.tft.match.repository;

import java.time.Instant;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MatchRenewRepositoryImpl implements MatchRenewRepository {
	private final RedisTemplate<String, Object> redisTemplate;
	private final String WAITING_QUEUE = "MATCH_RENEW_WAITING_QUEUE";

	@Override
	public boolean isPuuidInWaitingQueue(String puuid) {
		Double score = redisTemplate.opsForZSet().score(WAITING_QUEUE, puuid);
		return score != null && score > 0;
	}

	@Override
	public void addToWaitingQueue(String puuid) {
		redisTemplate.opsForZSet().add(WAITING_QUEUE, puuid, Instant.now().getEpochSecond());
	}
}
