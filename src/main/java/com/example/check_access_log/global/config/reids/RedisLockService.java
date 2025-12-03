package com.example.check_access_log.global.config.reids;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class RedisLockService {
    private final StringRedisTemplate stringRedisTemplate;

    //락 점유를 위한 메서드
    public boolean tryLock(String key, String value) {
        //setnx 명령어
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value);
    }

    // 추가: TTL 포함 버전
    public boolean tryLock(String key, String value, Duration ttl) {
        return Boolean.TRUE.equals(
                stringRedisTemplate.opsForValue()
                        .setIfAbsent(key, value, ttl)
        );
    }


    //락 해제 메서드(점유했던 키 삭제)
    public void releaseLock(String key) {
        stringRedisTemplate.delete(key);
    }

    // 추가: 소유권 확인하는 락 해제
    public void releaseLock(String key, String value) {
        String script = """
            if redis.call('get', KEYS[1]) == ARGV[1] then
                return redis.call('del', KEYS[1])
            else
                return 0
            end
            """;

        stringRedisTemplate.execute(
                new DefaultRedisScript<>(script, Long.class),
                Collections.singletonList(key),
                value
        );
    }
}
