package com.example.check_access_log.global.config.reids;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisManager {
    private final CustomObjectMapper customObjectMapper = new CustomObjectMapper();
    private final RedisTemplate<String, String> redisTemplate;
    private static final Long EXPIRE_SECONDS = 60 * 60 * 24 * 7L; // 1주일

    public <T> T get(RedisKeyPrefix keyPrefix, Long id, Class<T> clazz) {
        String jsonString = redisTemplate.opsForValue().get(generateCacheKey(keyPrefix, id));

        if (jsonString == null) {
            return null;
        }

        try {
            return customObjectMapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> getList(RedisKeyPrefix keyPrefix, Long id, Class<T> elementType) {
        String jsonString = redisTemplate.opsForValue().get(generateCacheKey(keyPrefix, id));

        if (jsonString == null) {
            return null;
        }

        try {
            JavaType listType = customObjectMapper.getTypeFactory().constructCollectionType(List.class, elementType);

            return customObjectMapper.readValue(jsonString, listType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void set(RedisKeyPrefix keyPrefix, Long id, T data) {
        try {
            String jsonString = customObjectMapper.writeValueAsString(data);

            redisTemplate.opsForValue().set(
                    generateCacheKey(keyPrefix, id),
                    jsonString,
                    Duration.ofSeconds(EXPIRE_SECONDS)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateCacheKey(RedisKeyPrefix keyPrefix, Long id) {
        return keyPrefix.getPrefix() + id;
    }

    public void expire(RedisKeyPrefix keyPrefix, Long month, Duration ttl) {
        redisTemplate.expire(generateCacheKey(keyPrefix, month), ttl);
    }

    public void delete(RedisKeyPrefix keyPrefix, Long id) {
        redisTemplate.delete(generateCacheKey(keyPrefix, id));
    }
}
