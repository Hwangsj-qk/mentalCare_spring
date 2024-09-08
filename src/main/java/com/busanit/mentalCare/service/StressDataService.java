package com.busanit.mentalCare.service;

import com.busanit.mentalCare.dto.StressDataDTO;
import com.busanit.mentalCare.model.StressData;
import com.busanit.mentalCare.repository.StressDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

// 스프링 시큐리티의 클래스를 상속받지 않는 UserService
@Service
@RequiredArgsConstructor
public class StressDataService {
    private final StressDataRepository userStressDataRepository;

    // 오늘날짜 데이터 불러오기
    public StressDataDTO getTodayStressData(String userId) throws IOException {
        StressData std = userStressDataRepository.todayStressData(userId);

        // 데이터가 없는 경우
        if (std == null) {
            throw new IOException("스트레스 데이터가 없습니다.");
        }

        return std.toDto();
    }

    // 해당날짜 데이터 불러오기
    public StressDataDTO getSelectedDateStressData(String userId, String stdDate) throws IOException {
        StressData std = userStressDataRepository.selectedDateStressData(userId, stdDate);

        // 데이터가 없는 경우
        if (std == null) {
            throw new IOException("스트레스 데이터가 없습니다.");
        }

        return std.toDto();
    }

}