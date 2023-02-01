package com.calendar.tft.matchStat.repository;

import java.util.Optional;

import com.calendar.tft.matchStat.entity.MonthlyMatchStat;
import org.springframework.data.repository.CrudRepository;

public interface MonthlyMatchStatRepository extends CrudRepository<MonthlyMatchStat, String> {
	Optional<MonthlyMatchStat> findByPuuidAndYearAndMonth(String puuid, int year, int month);
}
