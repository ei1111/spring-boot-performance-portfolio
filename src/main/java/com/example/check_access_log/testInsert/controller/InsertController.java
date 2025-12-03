package com.example.check_access_log.testInsert.controller;

import com.example.check_access_log.global.annotation.MethodInfoLogging;
import com.example.check_access_log.testInsert.service.InsertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InsertController {
    private final InsertService insertService;

    @GetMapping("/save")
    @MethodInfoLogging(description = "jpa의 save로 만건 저장")
    public void save() {
        insertService.save();
    }

    @GetMapping("/saveAll")
    @MethodInfoLogging(description = "jpa의 saveAll로 천건씩 저장")
    public void saveAll() {
        insertService.saveAll();
    }

    @GetMapping("/bulk-insert")
    @MethodInfoLogging(description = "bulkinsert로 천건씩 저장")
    public void bulkInsert() {
        insertService.bulkInsert();
    }

}
