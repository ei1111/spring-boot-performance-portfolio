package com.example.check_access_log.testRedis.controller;

import com.example.check_access_log.global.annotation.LogMethodExecution;
import com.example.check_access_log.testRedis.domain.dto.TestRedisRequest;
import com.example.check_access_log.testRedis.domain.dto.TestRedisResponse;
import com.example.check_access_log.testRedis.service.TestRedisService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
* 30만건 데이터로 테스트
* */
@RestController
@RequiredArgsConstructor
public class TestRedisContoller {
    private final TestRedisService testRedisService;

    @PostMapping("/no-cache")
    @LogMethodExecution(description = "조회시 일반 조회")
    public void selectNoCache(@RequestBody TestRedisRequest request) {
        List<TestRedisResponse> testRedisResponses = testRedisService.selectNoCache(request);
        System.out.println("testRedisResponses.size() = " + testRedisResponses.size());
    }

    @PostMapping("/redis-cache")
    @LogMethodExecution(description = "Redis Cache Aside 조회")
    public void selectRedisCache(@RequestBody TestRedisRequest request) {
        List<TestRedisResponse> testRedisResponses = testRedisService.selectRedisCache(request);
        System.out.println("testRedisResponses.size() = " + testRedisResponses.size());
    }

    @PostMapping("/not_write_back")
    @LogMethodExecution(description = "30만건 데이터 다른 테이블에 저장")
    public void selectAndSave(@RequestBody TestRedisRequest request) {
        testRedisService.selectAndSave(request);
    }


    @PostMapping("/write_back")
    @LogMethodExecution(description = "30만건 데이터 다른 테이블에 write_back 저장")
    public void selectAndSaveWriteBack(@RequestBody TestRedisRequest request) {
        testRedisService.writeBackSave(request);
    }
}
