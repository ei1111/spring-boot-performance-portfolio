package com.example.check_access_log.testRedis.async;


import com.example.check_access_log.global.config.reids.RedisKeyPrefix;
import com.example.check_access_log.global.config.reids.RedisManager;
import com.example.check_access_log.testRedis.domain.dto.TestRedisResponse;
import com.example.check_access_log.testRedis.domain.entity.TestRedisOther;
import com.example.check_access_log.testRedis.repository.TestRedisOtherRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestRedisWriteBack {
    private final RedisManager redisManager;
    private final TestRedisOtherRepository testRedisOtherRepository;

    @Async(value = "steadyExecutor")
    @Transactional(propagation  = Propagation.REQUIRES_NEW)
    public void asyncWriteToDatabase(Long month) {
        try {
            // Redis에서 데이터 조회
            List<TestRedisResponse> responses = redisManager.getList(
                    RedisKeyPrefix.DATE_DETAIL,
                    month,
                    TestRedisResponse.class
            );

            // Entity로 변환
            List<TestRedisOther> testRedisOthers = responses.stream()
                    .map(TestRedisResponse::from)
                    .toList();

            // 배치로 DB 저장
            for (TestRedisOther testRedisOther : testRedisOthers) {
                testRedisOtherRepository.save(testRedisOther);
            }

            //레디스에 5분 뒤 자동 데이터 삭제
            redisManager.expire(RedisKeyPrefix.DATE_DETAIL, month , Duration.ofMinutes(5));

            log.info("Successfully saved {} records for month: {}", testRedisOthers.size(), month);

        } catch (Exception e) {
            log.error("Failed to write back to database for month: {}", month, e);
            // 실패 시 재시도 로직 추가 가능
            // retryWriteBack(month);
        }
    }

    // 실패 시 재시도 (선택사항)
/*    @Async(value = "burstExecutor")
    @Retryable(
            value = {Exception.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public void retryWriteBack(Long month) {
        List<TestRedisResponse> responses = redisManager.getList(
                RedisKeyPrefix.DATE_DETAIL,
                month,
                TestRedisResponse.class
        );

        List<TestRedisOther> testRedisOthers = responses.stream()
                .map(TestRedisResponse::from)
                .toList();

        testRedisOtherRepository.saveAll(testRedisOthers);
    }*/
}
