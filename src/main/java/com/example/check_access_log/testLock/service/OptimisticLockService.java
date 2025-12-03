package com.example.check_access_log.testLock.service;

import com.example.check_access_log.testLock.dto.entity.TestLock;
import com.example.check_access_log.testLock.repository.LockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptimisticLockService {
    private final LockRepository lockRepository;


    @Transactional
    public void decreaseOptimisticLock(Long id) {
        TestLock testLock = lockRepository.findByIdWithOptimisticLock(id);
        testLock.decrease();
        lockRepository.saveAndFlush(testLock);
    }
}
