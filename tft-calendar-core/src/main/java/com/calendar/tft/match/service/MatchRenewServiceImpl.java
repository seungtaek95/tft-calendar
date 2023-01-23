package com.calendar.tft.match.service;

import com.calendar.tft.match.repository.MatchRenewRepository;
import com.calendar.tft.match.service.dto.MatchRenewResult;
import com.calendar.tft.summoner.entity.Summoner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchRenewServiceImpl implements MatchRenewService {
	private final MatchRenewRepository matchRenewRepository;

	public MatchRenewResult renew(Summoner summoner) {
		// 최근에 갱신했다면 return
		if (summoner.isRecentlyRenewed()) {
			return MatchRenewResult.recentlyRenewed(summoner.getPuuid());
		}

		// 이미 대기 큐에 있으면 return
		boolean isPuuidInWaitingQueue = matchRenewRepository.isPuuidInWaitingQueue(summoner.getPuuid());
		if (isPuuidInWaitingQueue) {
			return MatchRenewResult.alreadyProcessing(summoner.getPuuid());
		}

		matchRenewRepository.addToWaitingQueue(summoner.getPuuid());

		return MatchRenewResult.started(summoner.getPuuid());
	}
}
