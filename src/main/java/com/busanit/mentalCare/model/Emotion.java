package com.busanit.mentalCare.model;

import com.busanit.mentalCare.dto.EmotionDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "emotion")
public class Emotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emotionId;

    @Column(nullable = false)
    private String emotionWord;

    @Column(nullable = false)
    private String emotionType;

    public EmotionDTO toDto() {
        return new EmotionDTO(emotionId, emotionWord, emotionType);
    }
}
