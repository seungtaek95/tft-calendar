package com.calendar.tft.match.repository;

import java.util.List;

import com.calendar.tft.match.domain.entity.Match;

public interface CustomMatchRepository {
	Iterable<Match> saveAllIgnoreDuplicate(List<Match> matches);
}
