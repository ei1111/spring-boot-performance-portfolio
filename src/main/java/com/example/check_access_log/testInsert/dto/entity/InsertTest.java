package com.example.check_access_log.testInsert.dto.entity;

import com.example.check_access_log.testPartition.dto.entity.AccessLog;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class InsertTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String employeeNumber;
    private String userName;
    private LocalDateTime eventTime;
    private LocalDateTime createTime;

    private InsertTest(AccessLog accessLog) {
        this.employeeNumber = accessLog.getEmployeeNumber();
        this.userName = accessLog.getUserName();
        this.eventTime = accessLog.getEventTime();
        this.createTime = accessLog.getCreateTime();
    }

    public static InsertTest from(AccessLog accessLog) {
        return new InsertTest(accessLog);
    }
}
