package com.example.check_access_log.testLock.service;

import com.example.check_access_log.testLock.dto.entity.TestLock;
import com.example.check_access_log.testLock.repository.LockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JavaLockService {
    private final LockRepository lockRepository;

    // 모든 스레드가 공유하는 단일 락 객체
    private final Object lock = new Object();


    public void decreaseJavaLock(Long id) {
        synchronized (lock) {
            decreaseInTransaction(id);
        }
    }

    @Transactional
    public void decreaseInTransaction(Long id) {
        TestLock testLock = lockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        testLock.decrease();
        lockRepository.saveAndFlush(testLock);  // 명시적 저장
    }
}
