package com.tft.calendar.match.repository;

import com.tft.calendar.match.entity.Match;
import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match, String> {
}
