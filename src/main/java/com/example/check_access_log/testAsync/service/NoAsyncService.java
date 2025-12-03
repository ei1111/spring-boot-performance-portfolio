package com.example.check_access_log.testAsync.service;

import org.springframework.stereotype.Service;

@Service
public class NoAsyncService {

    public Integer test1() {
        try {
            Thread.sleep(5000);
            return 1;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public Integer test2() {
        try {
            Thread.sleep(5000);
            return 2;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public Integer test3() {
        try {
            Thread.sleep(5000);
            return 3;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
