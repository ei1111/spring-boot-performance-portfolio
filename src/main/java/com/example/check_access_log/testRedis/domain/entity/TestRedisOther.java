package com.example.check_access_log.testRedis.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestRedisOther {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime eventTime;

    private TestRedisOther(String name, LocalDateTime eventTime) {
        this.name = name;
        this.eventTime = eventTime;
    }

    public static TestRedisOther of(String name, LocalDateTime eventTime) {
        return new TestRedisOther(name, eventTime);
    }

    public static TestRedisOther from(TestRedis redis) {
        return TestRedisOther.of(redis.getName(), redis.getEventTime());
    }
}
