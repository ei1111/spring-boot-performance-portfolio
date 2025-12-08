package com.example.check_access_log.testPool.controller;

import com.example.check_access_log.global.annotation.MethodInfoLogging;
import com.example.check_access_log.testPool.service.TestPoolService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestpoolController {

    private final TestPoolService testPoolService;

    @GetMapping("/test/hikari/async")
    @MethodInfoLogging(description = "커넥션 풀 테스트")
    public String asyncTest() {
        List<CompletableFuture<String>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            futures.add(testPoolService.runQuery());
        }

        // 모든 작업 완료 대기
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        return "run async";
    }
}
