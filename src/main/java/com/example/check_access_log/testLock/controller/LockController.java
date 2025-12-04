package com.example.check_access_log.testLock.controller;

import com.example.check_access_log.global.annotation.MethodInfoLogging;
import com.example.check_access_log.testLock.domain.entity.LockType;
import com.example.check_access_log.testLock.lockFacede.OptimisticLockStockFacade;
import com.example.check_access_log.testLock.service.JavaLockService;
import com.example.check_access_log.testLock.service.NoLockService;
import com.example.check_access_log.testLock.service.PessimisticLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequiredArgsConstructor
public class LockController {
    private final NoLockService noLockService;
    private final JavaLockService javaLockService;
    private final OptimisticLockStockFacade optimisticLockStockFacade;
    private final PessimisticLockService pessimisticService;

    @MethodInfoLogging(description = "락이 없는 경우")
    @GetMapping("/noLock")
    public void noLock() throws InterruptedException {
        lockTest(LockType.NO);
    }


    @MethodInfoLogging(description = "자바 synchronized 경우")
    @GetMapping("/javaLock")
    public void javaLock() throws InterruptedException {
        lockTest(LockType.JAVA_SYNCHRONIZED);
    }


    @MethodInfoLogging(description = "비관적락 경우")
    @GetMapping("/pessimisticLock")
    public void pessimisticLock() throws InterruptedException {
        lockTest(LockType.PESSIMISTIC);
    }

    @MethodInfoLogging(description = "낙관적락 경우")
    @GetMapping("/optimisitcLock")
    public void optimisitcLock() throws InterruptedException {
        lockTest(LockType.OPTIMISTIC);
    }


    private void lockTest(LockType lockType) throws InterruptedException {
        //총 작업(task) 수 lockService.decreaseNoLock(1L)를 100번 실행
        int threadCount = 100;
        //스레드 풀 크기 = 32
        //100번에 작업 중 한번에 32번만 처리도록 함
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    switch (lockType){
                        case NO -> noLockService.decreaseNoLock(1L);
                        case JAVA_SYNCHRONIZED -> javaLockService.decreaseJavaLock(1L);
                        case PESSIMISTIC -> pessimisticService.decreasePessimisticeLock(1L);
                        case OPTIMISTIC -> optimisticLockStockFacade.decrease(1L);
                        default -> throw new RuntimeException();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }

        //작업이 종료 될때 까지 기다림
        latch.await();
    }
}
