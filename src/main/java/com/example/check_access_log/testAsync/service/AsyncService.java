package com.example.check_access_log.testAsync.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {

    @Async(value = "steadyExecutor")
    public CompletableFuture<Integer> futureTest1() {
        try {
            Thread.sleep(5000);
            return CompletableFuture.completedFuture(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Async(value = "steadyExecutor")
    public CompletableFuture<Integer> futureTest2() {
        try {
            Thread.sleep(5000);
            return CompletableFuture.completedFuture(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Async(value = "steadyExecutor")
    public CompletableFuture<Integer> futureTest3() {
        try {
            Thread.sleep(5000);
            return CompletableFuture.completedFuture(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
