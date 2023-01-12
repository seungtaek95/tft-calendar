package com.tft.calendar.matchStat.repository;

import com.tft.calendar.matchStat.entity.MonthlyMatchStat;
import org.springframework.data.repository.CrudRepository;

public interface MonthlyStatRepository extends CrudRepository<MonthlyMatchStat, String> {
}
