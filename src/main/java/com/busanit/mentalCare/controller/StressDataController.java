package com.busanit.mentalCare.controller;

import com.busanit.mentalCare.dto.StressDataDTO;
import com.busanit.mentalCare.service.StressDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/mcUserData")
@RestController
public class StressDataController {
    @Autowired
    StressDataService userStressDataService;

    /* 메소드 */
    // Id로 오늘 데이터 반환
    @GetMapping("/getTodayStressData")
    public ResponseEntity<StressDataDTO> getTodayStressData(@RequestParam String userId) throws IOException {
        StressDataDTO stdDto  = userStressDataService.getTodayStressData(userId);
        if (stdDto == null) {

        }
        return ResponseEntity.ok(stdDto);
    }

    // Id로 유저 선택날짜 데이터 반환
    @GetMapping("/getSelectedDateStressData")
    public ResponseEntity<StressDataDTO> getSelectedDateStressData(@RequestParam String userId, @RequestParam String stdDate) throws IOException {
        StressDataDTO stdDto = userStressDataService.getSelectedDateStressData(userId, stdDate);
        if (stdDto == null) {

        }
        return ResponseEntity.ok(stdDto);
    }
}
