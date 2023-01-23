package com.calendar.tft.matchRenew.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.calendar.tft.match.domain.enums.MatchRenewResultStatus;
import com.calendar.tft.match.repository.MatchRenewRepository;
import com.calendar.tft.match.service.MatchRenewServiceImpl;
import com.calendar.tft.match.service.dto.MatchRenewResult;
import com.calendar.tft.summoner.entity.Summoner;
import com.calendar.tft.summoner.entity.SummonerFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MatchRenewServiceTest {
	@Mock
	private MatchRenewRepository matchRenewRepository;

	@InjectMocks
	private MatchRenewServiceImpl matchRenewService;

	@Test
	@DisplayName("갱신중이지 않은 소환사 갱신 시도")
	void renew() {
		// given
		Summoner summoner = SummonerFixture.create();
		given(matchRenewRepository.isPuuidInWaitingQueue(summoner.getPuuid())).willReturn(false);

		// when
		MatchRenewResult result = matchRenewService.renew(summoner);

		// then
		assertThat(result.puuid()).isEqualTo(summoner.getPuuid());
		assertThat(result.resultStatus()).isEqualTo(MatchRenewResultStatus.STARTED);
	}

	@Test
	@DisplayName("갱신중인 소환사 갱신 시도")
	void renewAlreadyProcessing() {
		// given
		Summoner summoner = SummonerFixture.create();
		given(matchRenewRepository.isPuuidInWaitingQueue(summoner.getPuuid())).willReturn(true);

		// when
		MatchRenewResult result = matchRenewService.renew(summoner);

		// then
		assertThat(result.puuid()).isEqualTo(summoner.getPuuid());
		assertThat(result.resultStatus()).isEqualTo(MatchRenewResultStatus.ALREADY_PROCESSING);
	}
}
