package com.calendar.tft.summoner.repository;

import java.util.Optional;

import com.calendar.tft.summoner.entity.Summoner;
import org.springframework.data.repository.CrudRepository;

public interface SummonerRepository extends CrudRepository<Summoner, Long> {
	Optional<Summoner> findBySummonerNo(long summonerNo);
	Optional<Summoner> findByName(String name);
	Optional<Summoner> findByPuuid(String puuid);
}
