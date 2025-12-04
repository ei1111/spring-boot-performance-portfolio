package com.example.check_access_log.testPartition.domain.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record AccessLogRequest(
        @Schema(description = "시작일", example = "2025-10-01 00:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime startTime,

        @Schema(description = "종료일", example = "2025-10-31 23:59:59")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime endTime
) { }
