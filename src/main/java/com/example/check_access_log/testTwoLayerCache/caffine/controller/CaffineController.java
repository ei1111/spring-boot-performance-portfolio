package com.example.check_access_log.testTwoLayerCache.caffine.controller;


import com.example.check_access_log.global.annotation.MethodInfoLogging;
import com.example.check_access_log.testTwoLayerCache.caffine.domain.dto.CaffineRequest;
import com.example.check_access_log.testTwoLayerCache.caffine.service.CaffineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CaffineController {
    private final CaffineService caffineService;

    @GetMapping("/caffine/{id}")
    @MethodInfoLogging(description = "caffine cache 조회")
    public String findCaffine(@PathVariable Long id) {
        return caffineService.findById(id);
    }

    @PutMapping("/caffine")
    @MethodInfoLogging(description = "caffine cache 수정")
    public String updateCaffine(@RequestBody CaffineRequest request) {
        return caffineService.updateName(request);
    }

    @DeleteMapping("/caffine/{id}")
    @MethodInfoLogging(description = "caffine cache 삭제")
    public void deleteCaffine(@PathVariable Long id) {
        caffineService.delete(id);
    }
}
