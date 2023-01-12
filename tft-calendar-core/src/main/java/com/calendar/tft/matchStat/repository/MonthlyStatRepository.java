package com.calendar.tft.matchStat.repository;

import com.calendar.tft.matchStat.entity.MonthlyMatchStat;
import org.springframework.data.repository.CrudRepository;

public interface MonthlyStatRepository extends CrudRepository<MonthlyMatchStat, String> {
}
