package com.calendar.tft.matchStat.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.calendar.tft.matchStat.entity.MonthlyMatchStat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MonthlyMatchStatRepositoryTest {
	@Autowired
	private MonthlyMatchStatRepository sut;

	@Test
	void findFirstByPuuidOrderByIdDesc() {
		// given
		String puuid = "puuid1";
		MonthlyMatchStat monthlyMatchStat1 = new MonthlyMatchStat(puuid, 2023, 1, new LinkedList<>());
		MonthlyMatchStat monthlyMatchStat2 = new MonthlyMatchStat(puuid, 2023, 2, new LinkedList<>());
		sut.saveAll(List.of(monthlyMatchStat1, monthlyMatchStat2));

		// when
		Optional<MonthlyMatchStat> result = sut.findFirstByPuuidOrderByIdDesc(puuid);

		// then
		assertThat(result).isNotEmpty();
		assertThat(result.get().getMonth()).isEqualTo(2);

		sut.deleteAllById(List.of(monthlyMatchStat1.getId(), monthlyMatchStat2.getId()));
	}
}
