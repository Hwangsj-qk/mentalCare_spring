package com.busanit.mentalCare.model;

import com.busanit.mentalCare.dto.ChildrenCommentDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "children_comment")
@EntityListeners(AuditingEntityListener .class)
public class ChildrenComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long childrenId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private McUser user;

    private String childrenContent;

    @CreatedDate
    @Column(name = "children_time")
    private LocalDateTime childrenTime;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    @JsonIgnore
    private Comment comment;

    public ChildrenCommentDTO toDTO() {
        Long commentId = 0L;
        if(comment != null) {
            commentId = comment.getCommentId();
        }
        return new ChildrenCommentDTO(childrenId, childrenContent, childrenTime, user.getUserNickname(), commentId);

    }
}
