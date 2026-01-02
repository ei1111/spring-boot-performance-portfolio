package com.example.check_access_log.testTwoLayerCache.caffine.controller;


import com.example.check_access_log.global.annotation.LogMethodExecution;
import com.example.check_access_log.testTwoLayerCache.caffine.domain.dto.CaffineRequest;
import com.example.check_access_log.testTwoLayerCache.caffine.service.CaffineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CaffineController {
    private final CaffineService caffineService;

    @GetMapping("/caffine/{id}")
    @LogMethodExecution(description = "caffine cache 조회")
    public String findCaffine(@PathVariable Long id) {
        return caffineService.findById(id);
    }

    @PutMapping("/caffine")
    @LogMethodExecution(description = "caffine cache 수정")
    public String updateCaffine(@RequestBody CaffineRequest request) {
        return caffineService.updateName(request);
    }

    @DeleteMapping("/caffine/{id}")
    @LogMethodExecution(description = "caffine cache 삭제")
    public void deleteCaffine(@PathVariable Long id) {
        caffineService.delete(id);
    }
}
