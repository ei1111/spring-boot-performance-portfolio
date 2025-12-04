package com.example.check_access_log.testLock.service;

import com.example.check_access_log.testLock.domain.entity.TestLock;
import com.example.check_access_log.testLock.repository.LockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoLockService {
    private final LockRepository lockRepository;


    @Transactional
    public void decreaseNoLock(Long id) {
        TestLock testLock = lockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        testLock.decrease();
        lockRepository.saveAndFlush(testLock);
    }
}
