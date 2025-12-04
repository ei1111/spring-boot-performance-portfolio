package com.example.check_access_log.testInsert.repository;

import com.example.check_access_log.testInsert.domain.entity.InsertTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsertRepository extends JpaRepository<InsertTest, Long> {
}
