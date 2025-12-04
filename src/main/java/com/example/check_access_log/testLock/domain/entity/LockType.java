package com.example.check_access_log.testLock.domain.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LockType {
    NO("일반"),
    JAVA_SYNCHRONIZED("자바 Synchronized"),
    PESSIMISTIC("비관적락"),
    OPTIMISTIC("낙관적락");


    private final String description;
}
