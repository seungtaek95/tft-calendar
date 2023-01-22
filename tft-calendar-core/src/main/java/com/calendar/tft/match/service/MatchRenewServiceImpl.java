package com.calendar.tft.match.service;

import com.calendar.tft.match.repository.MatchRenewRepository;
import com.calendar.tft.match.service.dto.MatchRenewResult;
import com.calendar.tft.match.domain.enums.MatchRenewResultStatus;
import com.calendar.tft.summoner.entity.Summoner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchRenewServiceImpl {
	private final MatchRenewRepository matchRenewRepository;

	public MatchRenewResult renew(Summoner summoner) {
		boolean isPuuidProcessing = matchRenewRepository.isPuuidExist(summoner.getPuuid());
		if (isPuuidProcessing) {
			return new MatchRenewResult(summoner.getPuuid(), MatchRenewResultStatus.ALREADY_PROCESSING);
		}

		return new MatchRenewResult(summoner.getPuuid(), MatchRenewResultStatus.STARTED);
	}
}
