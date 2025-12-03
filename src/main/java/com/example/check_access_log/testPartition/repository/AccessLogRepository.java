package com.example.check_access_log.testPartition.repository;

import com.example.check_access_log.testPartition.dto.entity.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AccessLogRepository extends JpaRepository<AccessLog,Long> {
    @Query(value = """
    select id, employee_number, user_name, event_time, create_time
    from access_log
    where event_time >= :startDateTime and event_time <= :endDateTime
""", nativeQuery = true)
    List<AccessLog> findAccessLogByStartTimeAndEndTime(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
