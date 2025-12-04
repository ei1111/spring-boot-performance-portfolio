package com.example.check_access_log.testInsert.service;

import com.example.check_access_log.testInsert.domain.entity.InsertTest;
import com.example.check_access_log.testInsert.repository.InsertRepository;
import com.example.check_access_log.testPartition.domain.entity.AccessLog;
import com.example.check_access_log.testPartition.repository.AccessLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InsertService {
    private final InsertRepository insertRepository;
    private final AccessLogRepository accessLogRepository;
    private final JdbcTemplate jdbcTemplate;


    public void save() {
        LocalDateTime startDateTime = LocalDateTime.of(2025, 10, 1, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2025, 10, 31, 23, 59, 59);
        List<AccessLog> accessLogs = accessLogRepository.findAccessLogByStartTimeAndEndTime(startDateTime, endDateTime);

        save(accessLogs);
    }

    @Transactional
    public void save(List<AccessLog> accessLogs) {
        for (AccessLog accessLog : accessLogs) {
            InsertTest insertTest = InsertTest.from(accessLog);
            insertRepository.save(insertTest);
        }
    }


    public void saveAll() {
        LocalDateTime startDateTime = LocalDateTime.of(2025, 10, 1, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2025, 10, 31, 23, 59, 59);
        List<AccessLog> accessLogs = accessLogRepository.findAccessLogByStartTimeAndEndTime(startDateTime, endDateTime);


        saveAll(accessLogs);
    }

    @Transactional
    public void saveAll(List<AccessLog> accessLogs) {
        int batchSize = 1000;
        for (int i = 0; i < accessLogs.size(); i += batchSize) {
            int end = Math.min(i + batchSize, accessLogs.size());
            List<AccessLog> batchList = accessLogs.subList(i, end);
            List<InsertTest> insertTests = batchList.stream()
                    .map(InsertTest::from)
                    .toList();
            insertRepository.saveAll(insertTests); // 배치 저장
            insertRepository.flush(); // 필요하면 flush
        }
    }


    public void bulkInsert() {
        LocalDateTime startDateTime = LocalDateTime.of(2025, 10, 1, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2025, 10, 31, 23, 59, 59);
        List<AccessLog> accessLogs = accessLogRepository.findAccessLogByStartTimeAndEndTime(startDateTime, endDateTime);
        bulkInsert(accessLogs);
    }

    @Transactional
    public void bulkInsert(List<AccessLog> accessLogs) {
        int batchSize = 1000;
        String sql = "INSERT INTO insert_test (employee_number, user_name, event_time, create_time) VALUES (?, ?, ?, ?)";
        for (int i = 0; i < accessLogs.size(); i += batchSize) {
            int end = Math.min(i + batchSize, accessLogs.size());
            List<AccessLog> batchList = accessLogs.subList(i, end);

            jdbcTemplate.batchUpdate(sql, batchList, batchList.size(),
                    (ps, accessLog) -> {
                        ps.setString(1, accessLog.getEmployeeNumber());
                        ps.setString(2, accessLog.getUserName());
                        ps.setObject(3, accessLog.getEventTime());
                        ps.setObject(4, accessLog.getCreateTime());
                    });
        }
    }
}
