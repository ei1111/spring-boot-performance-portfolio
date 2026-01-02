package com.example.check_access_log.testAsync.controller;

import com.example.check_access_log.global.annotation.LogMethodExecution;
import com.example.check_access_log.testAsync.asyncFacade.AsyncFacde;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AysncController {
    private final AsyncFacde  asyncFacde;

    @GetMapping("/noAsync")
    @LogMethodExecution(description = "비동기 설정 전")
    public void noAsyncTest() {
        asyncFacde.noAsync();
    }

    @GetMapping("/async")
    @LogMethodExecution(description = "비동기 설정 후")
    public void asyncTest() {
        asyncFacde.async();
    }
}
