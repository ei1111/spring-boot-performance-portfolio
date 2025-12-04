package com.example.check_access_log.testTraffic.repository;

import com.example.check_access_log.testTraffic.domain.entity.Traffic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrafficRepository extends JpaRepository<Traffic,Long> {
}
