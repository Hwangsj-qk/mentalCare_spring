package com.busanit.mentalCare.controller;

import com.busanit.mentalCare.dto.SleepDataDTO;
import com.busanit.mentalCare.service.SleepDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/mcUserData")
@RestController
public class SleepDataController {
    @Autowired
    SleepDataService userSleepDataService;

    /* 메소드 */
    // Id로 오늘 데이터 반환
    @GetMapping("/getTodaySleepData")
    public ResponseEntity<SleepDataDTO> getTodaySleepData(@RequestParam String userId) throws IOException {
        SleepDataDTO sldDto = userSleepDataService.getTodaySleepData(userId);
        if (sldDto == null) {

        }
        return ResponseEntity.ok(sldDto);
    }

    // Id로 유저 선택날짜 데이터 반환
    @GetMapping("/getSelectedDateSleepData")
    public ResponseEntity<SleepDataDTO> getSelectedDateSleepData(@RequestParam String userId, @RequestParam String sldDate) throws IOException {
        SleepDataDTO sldDto = userSleepDataService.getSelectedDateSleepData(userId, sldDate);
        if (sldDto == null) {

        }
        return ResponseEntity.ok(sldDto);
    }
}
