package com.example.check_access_log.testAsync.asyncFacade;

import com.example.check_access_log.testAsync.service.AsyncService;
import com.example.check_access_log.testAsync.service.NoAsyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class AsyncFacde {
    private final AsyncService asyncService;
    private final NoAsyncService noAsyncService;



    public void noAsync() {
        Integer test1 = noAsyncService.test1();
        Integer test2 = noAsyncService.test2();
        Integer test3 = noAsyncService.test3();
        int result = test1 + test2 + test3;
        log.info("result: {}", result);
    }

    public void async() {
        CompletableFuture<Integer> test1 = asyncService.futureTest1();
        CompletableFuture<Integer> test2 = asyncService.futureTest2();
        CompletableFuture<Integer> test3 = asyncService.futureTest3();
        CompletableFuture.allOf(test1, test2, test3).join();

        Integer i1 = test1.join();
        Integer i2 = test2.join();
        Integer i3 = test3.join();

        int result = i1 + i2 + i3;
        log.info("result: {}", result);
    }
}
