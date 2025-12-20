package com.example.check_access_log.testRedis.service;



import com.example.check_access_log.global.config.reids.RedisKeyPrefix;
import com.example.check_access_log.global.config.reids.RedisManager;
import com.example.check_access_log.testRedis.async.TestRedisWriteBack;
import com.example.check_access_log.testRedis.domain.dto.TestRedisRequest;
import com.example.check_access_log.testRedis.domain.dto.TestRedisResponse;
import com.example.check_access_log.testRedis.domain.entity.TestRedis;
import com.example.check_access_log.testRedis.domain.entity.TestRedisOther;
import com.example.check_access_log.testRedis.repository.TestRedisOtherRepository;
import com.example.check_access_log.testRedis.repository.TestRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestRedisService {
    private final TestRedisRepository testRedisRepository;
    private final TestRedisOtherRepository testRedisOtherRepository;
    private final RedisManager redisManager;
    private final TestRedisWriteBack testRedisWriteBack;

    public List<TestRedisResponse> selectNoCache(TestRedisRequest request) {
        return testRedisRepository.findByEventTimeBetween(request.startDateTime(), request.endDateTime()).stream()
                .map(TestRedis::toResponse)
                .toList();
    }

    public List<TestRedisResponse> selectRedisCache(TestRedisRequest request) {
        long month = Long.parseLong(String.valueOf(request.startDateTime().getMonthValue()));

        List<TestRedisResponse> redisResponses = redisManager.getList(RedisKeyPrefix.DATE_DETAIL, month, TestRedisResponse.class);

        if (redisResponses != null) {
            return redisResponses;
        }

        redisResponses = testRedisRepository.findByEventTimeBetween(request.startDateTime(), request.endDateTime()).stream()
                .map(TestRedis::toResponse)
                .toList();


        redisManager.set(RedisKeyPrefix.DATE_DETAIL, month, redisResponses);
        return redisResponses;
    }

    @Transactional
    public void selectAndSave(TestRedisRequest request) {
        List<TestRedis> testRedis = testRedisRepository.findByEventTimeBetween(request.startDateTime(), request.endDateTime());
        testRedis.stream().forEach(s -> testRedisOtherRepository.save(TestRedisOther.from(s)));
    }

    @Transactional
    public void writeBackSave(TestRedisRequest request) {
        // 1. DB에서 데이터 조회
        List<TestRedisResponse> redisResponses = testRedisRepository
                .findByEventTimeBetween(request.startDateTime(), request.endDateTime())
                .stream()
                .map(TestRedis::toResponse)
                .toList();

        long month = Long.parseLong(String.valueOf(request.startDateTime().getMonthValue()));

        // 2. Redis에만 먼저 저장 (동기)
        redisManager.set(RedisKeyPrefix.DATE_DETAIL, month, redisResponses);

        // 3. DB 저장은 비동기로 처리 (응답 후 백그라운드에서 실행)
        testRedisWriteBack.asyncWriteToDatabase(month);
    }
}
