package com.busanit.mentalCare.service;

import com.busanit.mentalCare.dto.EmotionDiaryDTO;
import com.busanit.mentalCare.dto.EmotionDiaryViewDTO;
import com.busanit.mentalCare.dto.EmotionDiaryWriteDTO;
import com.busanit.mentalCare.model.Emotion;
import com.busanit.mentalCare.model.EmotionDiary;
import com.busanit.mentalCare.model.McUser;
import com.busanit.mentalCare.repository.EmotionDiaryRepository;
import com.busanit.mentalCare.repository.EmotionRepository;
import com.busanit.mentalCare.repository.McUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmotionDiaryService {

    private final EmotionDiaryRepository emotionDiaryRepository;
    private final McUserRepository userRepository;
    private final EmotionRepository emotionRepository;

    // 오늘 감정일지
    public EmotionDiaryViewDTO getTodayEmotionDiary(String userId) {
        EmotionDiary emotionDiary = emotionDiaryRepository.todayEmotionDiary(userId);

        String emotionType = emotionDiary.getEmotion().getEmotionType();
        String emotionWord = emotionDiary.getEmotion().getEmotionWord();

        EmotionDiaryViewDTO edViewDto = EmotionDiaryViewDTO.builder()
                .edId(emotionDiary.getEdId())
                .userId(userId)
                .emotionId(emotionDiary.getEdId())
                .emotionWord(emotionWord)
                .emotionType(emotionType)
                .edReason(emotionDiary.getEdReason())
                .edDate(emotionDiary.getEdDate())
                .build();

        return edViewDto;
    }

    // 해당 날짜 감정일지
    public EmotionDiaryViewDTO getSelectedDateEmotionDiary(String userId, String edDate) {
        EmotionDiary emotionDiary = emotionDiaryRepository.selectedDateEmotionDiary(userId, edDate);
        String emotionType = emotionDiary.getEmotion().getEmotionType();
        String emotionWord = emotionDiary.getEmotion().getEmotionWord();

        EmotionDiaryViewDTO edViewDto = EmotionDiaryViewDTO.builder()
                .edId(emotionDiary.getEdId())
                .userId(userId)
                .emotionId(emotionDiary.getEmotion().getEmotionId())
                .emotionWord(emotionWord)
                .emotionType(emotionType)
                .edReason(emotionDiary.getEdReason())
                .edDate(emotionDiary.getEdDate())
                .build();

        return edViewDto;
    }

    // 감정일지 작성
    public EmotionDiaryDTO writeEmotionDiary(String userId, EmotionDiaryDTO emotionDiaryDto) {
        Long emotionId = emotionDiaryDto.getEmotionId();
        String edReason = emotionDiaryDto.getEdReason();
        String edDate = emotionDiaryDto.getEdDate();

        McUser user = userRepository.findById(userId).orElse(null);
        Emotion emotion = emotionRepository.findById(emotionId).orElse(null);


        EmotionDiaryWriteDTO emotionDiaryWriteDto = new EmotionDiaryWriteDTO(userId, emotionId, edReason, edDate);

        return emotionDiaryRepository.save(emotionDiaryWriteDto.toEntity(user, emotion)).toDto();
    }

    // 감정일지 수정
    public EmotionDiaryDTO updateEmotionDiary(String userId, String edDate, EmotionDiaryDTO updateEmotionDiaryDto) {
        EmotionDiary emotionDiary = emotionDiaryRepository.selectedDateEmotionDiary(userId, edDate);
        if (emotionDiary == null) {
            throw new RuntimeException("감정일지 수정 오류 발생");
        }
        if (updateEmotionDiaryDto.getEmotionId() > 0) {
            emotionDiary.setEmotion(emotionRepository.findById(updateEmotionDiaryDto.getEmotionId()).orElse(null));
        }
        if (updateEmotionDiaryDto.getEdDate() != null) {
            emotionDiary.setEdReason(updateEmotionDiaryDto.getEdReason());
        }

        return emotionDiaryRepository.save(emotionDiary).toDto();
    }

    // 감정일지 삭제
    @Transactional
    public int deleteEmotionDiary(String userId, String edDate) {
        return emotionDiaryRepository.deleteEmotionDiary(userId, edDate);
    }
}
