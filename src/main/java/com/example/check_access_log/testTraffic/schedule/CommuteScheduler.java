package com.example.check_access_log.testTraffic.schedule;

import com.example.check_access_log.testTraffic.service.RedisQueueService;
import com.example.check_access_log.testTraffic.service.BatchFacadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommuteScheduler  {
    private final RedisQueueService redisQueueService;
    private final BatchFacadeService batchFacadeService;

    @Scheduled(fixedDelay = 3000)
    public void processBatchScheduled() {
        Long queueSize = redisQueueService.size();

        if (queueSize != null && queueSize > 0) {
            log.info("큐 사이즈: {}건 - 배치 처리 시작", queueSize);
            batchFacadeService.processBatchAsync(queueSize);
        }
    }
}
