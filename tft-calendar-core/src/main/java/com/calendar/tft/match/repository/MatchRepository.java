package com.calendar.tft.match.repository;

import com.calendar.tft.match.domain.entity.Match;
import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match, String> {
}
