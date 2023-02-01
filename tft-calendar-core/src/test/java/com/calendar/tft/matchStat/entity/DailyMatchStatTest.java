package com.calendar.tft.matchStat.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DailyMatchStatTest {
	@Test
	void accumulate() {
		// given
		DailyMatchStat dailyMatchStat1 = new DailyMatchStat(1, 2000, 5, 3);
		DailyMatchStat dailyMatchStat2 = new DailyMatchStat(1, 1000, 3, 2);

		// when
		dailyMatchStat1.accumulate(dailyMatchStat2);

		// then
		assertThat(dailyMatchStat1.getPlaytimeInSeconds()).isEqualTo(3000);
		assertThat(dailyMatchStat1.getPlayedGameCount()).isEqualTo(8);
		assertThat(dailyMatchStat1.getAveragePlacement()).isEqualTo((float)(5 * 3 + 3 * 2) / (5 + 3));
	}
}
