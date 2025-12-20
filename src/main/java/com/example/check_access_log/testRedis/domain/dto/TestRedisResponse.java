package com.example.check_access_log.testRedis.domain.dto;

import com.example.check_access_log.testRedis.domain.entity.TestRedisOther;
import java.time.LocalDateTime;


public record TestRedisResponse(
        Long id,
        String name,
        LocalDateTime eventTime
) {
    public static TestRedisOther from(TestRedisResponse response) {
        return new TestRedisOther(response.name, response.eventTime);
    }
}
