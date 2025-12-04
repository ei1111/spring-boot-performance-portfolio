package com.example.check_access_log.testNplusOne.repository;


import com.example.check_access_log.testNplusOne.entity.Team;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team,Long> {
    @Query("""
       select t from Team t 
  """)
    List<Team> findAllTeamMemberNicknames();
    // 1) fetch join (JPQL)
    @Query("select t from Team t join fetch t.members")
    List<Team> findAllFetchJoin();

    @EntityGraph(attributePaths = "members")
    List<Team> findAll();
}
