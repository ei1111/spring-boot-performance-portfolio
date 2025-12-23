package com.example.check_access_log.testTwoLayerCache.caffine.service;

import com.example.check_access_log.testTwoLayerCache.caffine.domain.dto.CaffineRequest;
import com.example.check_access_log.testTwoLayerCache.caffine.domain.entity.Caffine;
import com.example.check_access_log.testTwoLayerCache.caffine.repository.CaffineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CaffineService {
    private final CaffineRepository caffineRepository;

    @Cacheable(cacheNames = "member", key = "'memberId:' + #id")
    public String findById(Long id) {
        Caffine caffine = caffineRepository.findById(id).orElseThrow();
        return caffine.getName();
    }

    @Transactional
    @CachePut(cacheNames = "member", key = "'memberId:' + #request.id()")
    public String updateName(CaffineRequest request) {
        Caffine caffine = caffineRepository.findById(request.id()).orElseThrow();
        caffine.changeName(request.name());
        return caffine.getName();
    }

    @Transactional
    @CacheEvict(cacheNames = "member",  key = "'memberId:' + #id")
    public void delete(Long id) {
        Caffine caffine = caffineRepository.findById(id).orElseThrow();
        caffineRepository.delete(caffine);
    }
}
