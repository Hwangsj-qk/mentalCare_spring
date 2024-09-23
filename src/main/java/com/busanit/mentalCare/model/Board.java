package com.busanit.mentalCare.model;

import com.busanit.mentalCare.dto.BoardDTO;
import com.busanit.mentalCare.dto.CommentDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "board")
public class Board {

    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 자동 생성
    private Long boardId;

    @Enumerated(EnumType.STRING)
    private TagType boardTag;

    private String boardTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    private McUser user;

    @CreatedDate // 엔티티가 처음 생성될 때 자동으로 날짜나 시간을 기록하기 위해 사용되는 애노테이션
    private LocalDateTime boardTime;

    // 상대적 시간을 나타내는 필드
    private String calculateTime;

    private String boardContent;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments;

    // 공감 갯수를 담을 필드
    @ColumnDefault("0")
    private Integer boardHeartCount;

    @ColumnDefault("0")
    private int boardCommentCount;

    public void setCalculateTime(String calculateTime) {
        calculateTime = Time.getTimeDifference(boardTime, LocalDateTime.now());
        this.calculateTime = calculateTime;
    }

    // 엔티티 -> DTO
    public BoardDTO toDTO() {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        if(comments != null) {
            commentDTOList = comments.stream().map(Comment::toDTO).toList();
        }
        return new BoardDTO(boardId, boardTag, boardTitle, boardTime, calculateTime, boardContent,
                user.getUserNickname(), boardHeartCount, boardCommentCount, commentDTOList);
    }

}
