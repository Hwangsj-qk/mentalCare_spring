package com.busanit.mentalCare.repository;

import com.busanit.mentalCare.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // board_id를 통해 해당 게시글에 작성된 댓글 찾기
    List<Comment> findByBoardBoardId(Long boardId);

}
