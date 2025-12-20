package com.example.check_access_log.testRedis.domain.entity;

import com.example.check_access_log.testRedis.domain.dto.TestRedisResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class TestRedis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime eventTime;


    public TestRedisResponse toResponse() {
        return new TestRedisResponse(id, name, eventTime);
    }
}
