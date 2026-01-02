package com.example.check_access_log.testPartition.controller;

import com.example.check_access_log.global.annotation.LogMethodExecution;
import com.example.check_access_log.testPartition.domain.dto.request.AccessLogRequest;
import com.example.check_access_log.testPartition.domain.dto.response.AccessLogResponse;
import com.example.check_access_log.testPartition.service.AccessLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccessLogController {
    private final AccessLogService accessLogService;

   
    @PostMapping(value = "/access")
    @LogMethodExecution(description = "데이터 조회")
    public List<AccessLogResponse> findDataFromDate(@RequestBody AccessLogRequest accessLogRequest) {
        return accessLogService.findTheDate(accessLogRequest);
    }
}
