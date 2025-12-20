package com.example.check_access_log.testRedis.repository;

import com.example.check_access_log.testRedis.domain.entity.TestRedisOther;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRedisOtherRepository extends JpaRepository<TestRedisOther, Long> {
}
