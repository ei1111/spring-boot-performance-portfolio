package com.example.check_access_log.testNplusOne.service;


import com.example.check_access_log.testNplusOne.entity.Member;
import com.example.check_access_log.testNplusOne.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {
    private final TeamRepository teamRepository;

    public List<String> findAllTeamMemberNicknames() {
        return teamRepository.findAllTeamMemberNicknames().stream()
                .flatMap(team -> team.getMembers().stream())
                .map(Member::getNickname)
                .toList();
    }

    public List<String> findAllTeamMemberNicknamesFetchJoin() {
        return teamRepository.findAllFetchJoin().stream()
                .flatMap(team -> team.getMembers().stream())
                .map(Member::getNickname)
                .toList();
    }


    public List<String> findAllTeamMemberNicknamesByEntityGraph() {
        return teamRepository.findAll().stream()
                .flatMap(team -> team.getMembers().stream())
                .map(Member::getNickname)
                .toList();
    }

}
