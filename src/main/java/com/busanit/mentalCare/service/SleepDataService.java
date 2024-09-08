package com.busanit.mentalCare.service;

import com.busanit.mentalCare.dto.SleepDataDTO;
import com.busanit.mentalCare.model.SleepData;
import com.busanit.mentalCare.repository.SleepDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SleepDataService {
    private final SleepDataRepository userSleepDataRepository;

    // 오늘날짜 데이터 불러오기
    public SleepDataDTO getTodaySleepData(String userId) throws IOException {
        SleepData sld = userSleepDataRepository.todaySleepData(userId);

        // 데이터가 없는 경우
        if (sld == null) {
            throw new IOException("수면 데이터가 없습니다.");
        }

        return sld.toDto();
    }

    // 해당날짜 데이터 불러오기
    public SleepDataDTO getSelectedDateSleepData(String userId, String stdDate) throws IOException {
        SleepData sld = userSleepDataRepository.selectedDateSleepData(userId, stdDate);

        // 데이터가 없는 경우
        if (sld == null) {
            throw new IOException("수면 데이터가 없습니다.");
        }

        return sld.toDto();
    }

}
