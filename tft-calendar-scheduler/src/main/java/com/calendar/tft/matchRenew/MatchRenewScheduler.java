package com.calendar.tft.matchRenew;

import java.util.Optional;

import com.calendar.tft.match.service.MatchRenewQueue;
import com.calendar.tft.match.service.MatchRenewService;
import com.calendar.tft.summoner.entity.Summoner;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchRenewScheduler {
	private final MatchRenewQueue matchRenewQueue;
	private final MatchRenewService matchRenewService;

	@Scheduled(fixedRate = 2000L)
	public void test() {
		Optional<Summoner> summoner = matchRenewService.getOldestWaitingSummoner();
		summoner.ifPresent(this::work);
	}

	private void work(Summoner summoner) {
		matchRenewQueue.addToProcessingQueue(summoner.getPuuid());

		try {
			matchRenewService.manualRenew(summoner);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			matchRenewQueue.removeFromProcessingQueue(summoner.getPuuid());
		}

	}
}
