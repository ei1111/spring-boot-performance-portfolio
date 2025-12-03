package com.example.check_access_log.testTraffic.service;


import com.example.check_access_log.global.config.reids.RedisLockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchFacadeService {
    private final RedisQueueService redisQueueService;
    private final TrafficService trafficService;
    private final RedisLockService redisLockService;
    private static final String LOCK_KEY = "lock:event:batch";

    @Async(value = "burstExecutor")
    public void processBatchAsync(Long queueSize) {
        String lockValue = UUID.randomUUID().toString();
        boolean lockAcquired = false;

        try {
            //  큐 사이즈에 따라 TTL 동적 계산
            long estimatedSeconds = (queueSize / 1000) + 5;  // 1000건당 1초 + 여유 5초
            long ttl = Math.min(estimatedSeconds, 30);  // 최대 30초

            lockAcquired = redisLockService.tryLock(LOCK_KEY, lockValue, Duration.ofSeconds(ttl));

            if (!lockAcquired) {
                log.info("락을 점유 중입니다.");
                return;
            }

            // 원자적으로 모든 데이터 가져오기
            List<String> requests = new ArrayList<>();
            String item;
            while ((item = redisQueueService.pop()) != null) {
                requests.add(item);

                // 안전장치: 너무 많은 데이터는 배치로 나눠서 처리
                if (requests.size() >= 10000) {
                    log.info("배치 처리: {}건", requests.size());
                    trafficService.save(requests);
                    requests.clear();
                }
            }

            // 남은 데이터 처리
            if (!requests.isEmpty()) {
                log.info("최종 배치 처리: {}건", requests.size());
                trafficService.save(requests);
            }
        } catch (Exception e) {
            log.error("배치 처리 중 오류", e);
        } finally {
            //  락을 획득했을 때만 해제
            if (lockAcquired) {
                redisLockService.releaseLock("lock:event:batch", lockValue);
            }
        }
    }
}
