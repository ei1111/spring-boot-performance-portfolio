package com.example.check_access_log.testTraffic.domain.dto.request;

import com.example.check_access_log.testTraffic.domain.entity.Traffic;
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
