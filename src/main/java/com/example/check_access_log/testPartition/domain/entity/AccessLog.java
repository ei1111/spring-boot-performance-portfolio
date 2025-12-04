package com.example.check_access_log.testPartition.domain.entity;

import com.example.check_access_log.testPartition.domain.dto.response.AccessLogResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String employeeNumber;
    private String userName;
    private LocalDateTime eventTime;
    private LocalDateTime createTime;

    public AccessLogResponse toResponse() {
        return new AccessLogResponse(employeeNumber,userName,eventTime);
    }
}
