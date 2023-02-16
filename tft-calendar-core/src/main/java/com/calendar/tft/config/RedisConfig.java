package com.calendar.tft.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
	@Bean
	public LettuceConnectionFactory lettuceConnectionFactory(
			@Value("${spring.data.redis.host}") String host,
			@Value("${spring.data.redis.port}") String port) {
		return new LettuceConnectionFactory(host, Integer.parseInt(port));
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);

		return template;
	}
}
