package com.example.check_access_log.testLock.lockFacede;

import com.example.check_access_log.testLock.service.OptimisticLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptimisticLockStockFacade {

    private final OptimisticLockService optimisticLockService;


    public void decrease(Long id) throws InterruptedException {
        int retryCount = 0;
        int maxRetry = 100;
        while (retryCount < maxRetry) {
            try {
                optimisticLockService.decreaseOptimisticLock(id);

                break;
            } catch (Exception e) {
                retryCount++;
                if (retryCount >= maxRetry) {
                    throw e; // 최대 시도 초과하면 예외 던짐
                }
                Thread.sleep(50);
            }
        }
    }
}

