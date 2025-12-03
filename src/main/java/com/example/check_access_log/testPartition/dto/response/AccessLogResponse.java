package com.example.check_access_log.testPartition.dto.response;

import java.time.LocalDateTime;

public record AccessLogResponse(
        String employeeNumber,
        String userName,
        LocalDateTime eventTime
) {
}
