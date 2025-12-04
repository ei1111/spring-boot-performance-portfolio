package com.example.check_access_log.testNplusOne.controller;


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
    public List<String> nPlusTest() {
        return teamService.findAllTeamMemberNicknames();
    }

    @GetMapping("/fetchJoin")
    public List<String> fetchJoinTest() {
        return teamService.findAllTeamMemberNicknamesFetchJoin();
    }

    @GetMapping("/entityGraph")
    public List<String> entityGraphTest() {
        return teamService.findAllTeamMemberNicknamesByEntityGraph();
    }
}
