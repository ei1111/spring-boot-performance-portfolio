package com.example.check_access_log.testPartition.service;

import com.example.check_access_log.testPartition.domain.entity.AccessLog;
import com.example.check_access_log.testPartition.domain.dto.request.AccessLogRequest;
import com.example.check_access_log.testPartition.domain.dto.response.AccessLogResponse;
import com.example.check_access_log.testPartition.repository.AccessLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccessLogService {
    private final AccessLogRepository accessLogRepository;


    public List<AccessLogResponse> findTheDate(AccessLogRequest accessLogRequest) {
        List<AccessLog> accessLogs = accessLogRepository.findAccessLogByStartTimeAndEndTime(accessLogRequest.startTime(), accessLogRequest.endTime());
        return accessLogs.stream()
                .map(v -> v.toResponse())
                .toList();
    }
}
