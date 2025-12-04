package com.example.check_access_log.testTraffic.service;


import com.example.check_access_log.global.config.reids.CustomObjectMapper;
import com.example.check_access_log.testTraffic.domain.dto.request.TrafficRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisQueueService {
    private final StringRedisTemplate redisTemplate;
    private final CustomObjectMapper mapper = new CustomObjectMapper();

    public void enqueue(TrafficRequest request) {
        try {
            String json = mapper.writeValueAsString(request);
            redisTemplate.opsForList().rightPush("event:queue", json);
            log.info("이벤트 큐 적재 완료: {}", request);
        } catch (Exception e) {
            log.error("이벤트 큐 적재 실패", e);
        }
    }

    public String pop() {
        return redisTemplate.opsForList().leftPop("event:queue");
    }

    public Long size() {
        return redisTemplate.opsForList().size("event:queue");
    }
}
