package com.example.check_access_log.testTwoLayerCache.caffine.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CaffineCacheType{
    MEMBER_PROFILE("member", 5, 10000, "memberId");
    //캐시명
    private final String cacheName;
    //캐시 시간
    private final int expiredAfterWriteTime;
    //최대 보관 갯수
    private final int maximumSize;
    //키 명칭
    private final String keyPrefix;

    public String key(Long id) {
        return keyPrefix  + ":"+ id;
    }
}
