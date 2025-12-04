package com.example.check_access_log.testLock.repository;

import com.example.check_access_log.testLock.domain.entity.TestLock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface LockRepository extends JpaRepository<TestLock,Long> {
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from TestLock s where s.id=:id")
    TestLock findByIdWithPessimisticLock(Long id);

    @Lock(value = LockModeType.OPTIMISTIC)
    @Query("select s from TestLock s where s.id = :id")
    TestLock findByIdWithOptimisticLock(Long id);
}
