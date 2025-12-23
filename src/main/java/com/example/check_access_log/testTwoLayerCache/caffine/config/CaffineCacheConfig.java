package com.example.check_access_log.testTwoLayerCache.caffine.config;


import com.example.check_access_log.testTwoLayerCache.caffine.domain.entity.CaffineCacheType;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CaffineCacheConfig {

    @Bean
    public List<CaffeineCache> caffeineCaches() {
        return Arrays.stream(CaffineCacheType.values())
                //캐시 이름
                .map(cache -> new CaffeineCache(cache.getCacheName(), Caffeine.newBuilder().recordStats()
                        //5분 지나면 사라짐(최신화를 위해 짧게 가져감)
                        .expireAfterWrite(cache.getExpiredAfterWriteTime(), TimeUnit.MINUTES)
                        //캐시에 최대 10000개 보관
                        .maximumSize(cache.getMaximumSize())
                        .build()))
                .toList();
    }

    @Bean
    public CacheManager cacheManager(List<CaffeineCache> caffeineCaches) {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caffeineCaches);
        return cacheManager;
    }
}
