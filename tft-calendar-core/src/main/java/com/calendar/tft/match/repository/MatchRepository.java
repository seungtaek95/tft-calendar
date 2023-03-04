package com.calendar.tft.match.repository;

import java.util.List;

import com.calendar.tft.match.domain.entity.Match;
import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match, String>, CustomMatchRepository {
	List<Match> findAllByMatchIdIn(List<String> matchIds);
}
