package com.example.check_access_log.testPool.service;

import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestPoolService {

    private final JdbcTemplate jdbcTemplate;

    @Async(value = "steadyExecutor")
    public CompletableFuture<String> runQuery() {
        jdbcTemplate.queryForObject("SELECT SLEEP(2)", String.class);
        return CompletableFuture.completedFuture("done");
    }
}
