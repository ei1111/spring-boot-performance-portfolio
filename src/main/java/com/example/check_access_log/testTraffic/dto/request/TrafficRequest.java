package com.example.check_access_log.testTraffic.dto.request;

import com.example.check_access_log.testTraffic.dto.entity.Traffic;
import lombok.Getter;


@Getter
public class TrafficRequest {
    private Long id;
    private String name;

    public Traffic toTrafficEntity() {
        return Traffic.builder()
                .name(name)
                .build();
    }
}
