package com.example.check_access_log.testTraffic.service;


import com.example.check_access_log.global.config.reids.CustomObjectMapper;
import com.example.check_access_log.testTraffic.dto.entity.Traffic;
import com.example.check_access_log.testTraffic.dto.request.TrafficRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrafficService {
    private final JdbcTemplate jdbcTemplate;
    private final CustomObjectMapper customObjectMapper = new CustomObjectMapper();

    public void save(List<String> requests) {
        long start = System.currentTimeMillis();
        List<Traffic> traffics = getTraffic(requests);
        int total = traffics.size();

        //  데이터 크기에 따라 동적으로 배치 크기 결정
        int batchSize = determineBatchSize(total);

        for (int i = 0; i < total; i += batchSize) {
            int end = Math.min(i + batchSize, total);
            List<Traffic> subList = traffics.subList(i, end);

            try {
                saveChunk(subList , batchSize);
                log.info("배치 저장 성공: {} ~ {}", i + 1, end);
            } catch (Exception e) {
                log.error("배치 저장 실패: {} ~ {}", i + 1, end, e);
                // 실패한 데이터만 Redis 등에 재처리 큐로 보낼 수도 있음
            }
        }

        log.info("DB 저장 완료: {}건", traffics.size());

        long end = System.currentTimeMillis();
        System.out.println("전체 처리 시간 = " + (end - start) + " ms");
    }

    /**
     *  데이터 양에 따라 배치 크기 동적 결정
     */
    private int determineBatchSize(int total) {
        if (total < 500) {
            return 50;        // 소량 → 안정성 중심
        } else if (total < 1_000) {
            return 100;
        } else if (total < 5_000) {
            return 500;
        } else {
            return 1_000;  // 대량 → 성능 중심
        }
    }


    private List<Traffic> getTraffic(List<String> requests) {
        List<Traffic> traffics = requests.stream()
                .map(request -> {
                    try {
                        TrafficRequest testDto = customObjectMapper.readValue(request, TrafficRequest.class);
                        return testDto.toTrafficEntity();
                    } catch (JsonProcessingException e) {
                        log.error("JSON 파싱 실패: {}", request, e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
        return traffics;
    }


    @Transactional
    public void saveChunk(List<Traffic> traffics, int batchSize) {
        String sql = "INSERT INTO traffics (name) VALUES (?)";
        jdbcTemplate.batchUpdate(sql, traffics, batchSize,
                (ps, traffic) -> ps.setString(1, traffic.getName()));
    }

}
