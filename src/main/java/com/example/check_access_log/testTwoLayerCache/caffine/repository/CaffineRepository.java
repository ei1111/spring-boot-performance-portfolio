package com.example.check_access_log.testTwoLayerCache.caffine.repository;

import com.example.check_access_log.testTwoLayerCache.caffine.domain.entity.Caffine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaffineRepository extends JpaRepository<Caffine, Long> {
}
