package com.example.check_access_log.testNplusOne.controller;


import com.example.check_access_log.global.annotation.MethodInfoLogging;
import com.example.check_access_log.testNplusOne.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @GetMapping("/nPlus")
    @MethodInfoLogging(description = "n Plus one 문제 발생")
    public List<String> nPlusTest() {
        return teamService.findAllTeamMemberNicknames();
    }

    @GetMapping("/fetchJoin")
    @MethodInfoLogging(description = "fetchJoin 사용")
    public List<String> fetchJoinTest() {
        return teamService.findAllTeamMemberNicknamesFetchJoin();
    }

    @GetMapping("/entityGraph")
    @MethodInfoLogging(description = "entityGraph 사용")
    public List<String> entityGraphTest() {
        return teamService.findAllTeamMemberNicknamesByEntityGraph();
    }
}
