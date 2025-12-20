package com.example.check_access_log.testRedis.repository;

import com.example.check_access_log.testRedis.domain.entity.TestRedis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TestRedisRepository extends JpaRepository<TestRedis, Long>{
    List<TestRedis> findByEventTimeBetween(LocalDateTime start, LocalDateTime end);
}
