package com.calendar.tft.match.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MatchRenewRepositoryImpl implements MatchRenewRepository {
	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public boolean isPuuidExist(String puuid) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(puuid));
	}
}
