package com.example.check_access_log.testTwoLayerCache.twoLayerCache.controller;


import com.example.check_access_log.global.annotation.MethodInfoLogging;
import com.example.check_access_log.testTwoLayerCache.twoLayerCache.service.TwoLayerCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TwoLayerCacheController {
    private final TwoLayerCacheService twoLayerCacheService;

    @GetMapping("/twoLayerCache/{id}")
    @MethodInfoLogging(description = "2 Layer Cache 조회")
    public String findTwoLayerCache(@PathVariable Long id) {
        return twoLayerCacheService.findById(id);
    }
}
