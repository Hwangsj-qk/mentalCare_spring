package com.busanit.mentalCare.controller;

import com.busanit.mentalCare.model.McUser;
import com.busanit.mentalCare.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boardHeart")
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/count/{boardId}")
    public ResponseEntity<Map<String, Integer>> addHeart(@PathVariable("boardId") Long boardId, @RequestBody McUser user) {
        Integer count = heartService.addHeart(boardId, user);

        // 공감 갯수를 표현하기 위해 공감을 누른 사용자와 갯수를 맵으로 표현
        Map<String, Integer> map = new HashMap<>();
        map.put("count", count);
        return ResponseEntity.ok(map);
    }
}
