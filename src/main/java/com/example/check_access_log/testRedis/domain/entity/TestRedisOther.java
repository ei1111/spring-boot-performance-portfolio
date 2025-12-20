package com.example.check_access_log.testRedis.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class TestRedisOther {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime eventTime;

    TestRedisOther(String name, LocalDateTime eventTime) {
        this.name = name;
        this.eventTime = eventTime;
    }

    public static TestRedisOther from(TestRedis redis) {
        return new TestRedisOther(redis.getName(), redis.getEventTime());
    }
}
