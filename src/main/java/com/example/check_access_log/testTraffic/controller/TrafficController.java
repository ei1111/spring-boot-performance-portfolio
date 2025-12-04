package com.example.check_access_log.testTraffic.controller;



import com.example.check_access_log.testTraffic.domain.dto.request.TrafficRequest;
import com.example.check_access_log.testTraffic.service.RedisQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class TrafficController {
    private final RedisQueueService redisQueueService;

    @PostMapping("/traffic")
    public void bulkInsertProcess(@RequestBody TrafficRequest request) {
        redisQueueService.enqueue(request);
    }

}
