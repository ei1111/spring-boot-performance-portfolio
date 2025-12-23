package com.example.check_access_log.testTwoLayerCache.twoLayerCache.service;


import com.example.check_access_log.testTwoLayerCache.caffine.domain.entity.Caffine;
import com.example.check_access_log.testTwoLayerCache.caffine.domain.entity.CaffineCacheType;
import com.example.check_access_log.testTwoLayerCache.caffine.repository.CaffineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TwoLayerCacheService {
    private final CacheManager caffeineCacheManager;
    private final RedisTemplate<String, String> redisTemplate;
    private final CaffineRepository repository;

    public String findById(Long id) {
        String key =   CaffineCacheType.MEMBER_PROFILE.key(id);

        //1.Caffeine
        String cacheName = CaffineCacheType.MEMBER_PROFILE.getCacheName();
        Cache caffeine = caffeineCacheManager.getCache(cacheName);
        String value = caffeine.get(key, String.class);

        if (value != null) {
            return value;
        }

        //2.Redis
        value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            caffeine.put(key, value);
            return value;
        }

        //3.DB
        Caffine entity = repository.findById(id).orElseThrow();
        value = entity.getName();

        //4.캐시 저장
        caffeine.put(key, value);
        //12시간
        redisTemplate.opsForValue().set(
                key,
                value,
                Duration.ofHours(12)
        );

        return value;
    }
}
