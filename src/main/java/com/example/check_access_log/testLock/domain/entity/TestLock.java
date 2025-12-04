package com.example.check_access_log.testLock.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class TestLock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int stock;

   /* @Version
    private Long version = 0L;*/

    public void decrease() {
        if (this.stock <= 0) {
            throw new RuntimeException("error");
        }
        this.stock--;
    }
}
