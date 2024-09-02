package com.busanit.mentalCare.service;

import com.busanit.mentalCare.dto.CommentDTO;
import com.busanit.mentalCare.entity.Board;
import com.busanit.mentalCare.entity.ChildrenComment;
import com.busanit.mentalCare.entity.Comment;
import com.busanit.mentalCare.entity.User;
import com.busanit.mentalCare.repository.BoardRepository;
import com.busanit.mentalCare.repository.CommentRepository;
import com.busanit.mentalCare.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;


    // 엔티티 -> DTO로 변환하여 전달
    @Transactional
    public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(Comment::toDTO).toList();
    }

    @Transactional
    public List<CommentDTO> getCommentByBoardId(Long boardId) {
        List<Comment> commentList = commentRepository.findByBoardBoardId(boardId);
        return commentList.stream().map(Comment::toDTO).toList();
    }

    @Transactional
    public CommentDTO createComment(CommentDTO dto) {
        // 1. 게시판(Board)을 데이터베이스에서 가져옵니다.
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new RuntimeException("존재하지 않은 게시판"));

        // 2. 유저 정보를 데이터베이스에서 가져옵니다.
        User user = userRepository.findByUserNickname(dto.getUserNickname());

        // 3. 댓글(Comment) 객체를 생성합니다.
        Comment comment = dto.toEntity(board, user);

        // 4. 게시판의 CommentCount를 1 증가시킵니다.
        board.setBoardCommentCount(board.getBoardCommentCount() + 1);

        // 5. 게시판(Board) 객체를 데이터베이스에 저장합니다.
        boardRepository.save(board);

        // 6. 댓글(Comment) 객체를 데이터베이스에 저장합니다.
        Comment saved = commentRepository.save(comment);

        // 7. 저장된 댓글 정보를 DTO로 변환하여 반환합니다.
        return saved.toDTO();
    }


    @Transactional
    public CommentDTO updateComment(Long comment_id, CommentDTO updateComment) {
        Comment comment = commentRepository.findById(comment_id).orElse(null);
        if (comment != null) {
            if (updateComment.getCommentContent() != null) {
                comment.setCommentContent(updateComment.getCommentContent());
            }

            Comment saved = commentRepository.save(comment);
            return saved.toDTO();
        } else {
            return null;
        }
    }

    @Transactional
    public CommentDTO deleteComment(Long comment_id) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> new IllegalArgumentException("해당 댓글은 이미 존재하지 않습니다."));

        // 댓글에 답글이 있는지 확인
        List<ChildrenComment> childrenComments = comment.getChildrenComments();

        if (childrenComments != null && !childrenComments.isEmpty()) {
            // 답글이 있으면 댓글 내용을 "삭제된 댓글입니다."로 변경
            comment.setCommentContent("삭제된 댓글입니다.");
            commentRepository.save(comment);

            // 답글 수만큼 board의 댓글 수 감소시킨 후 board 저장
            Board board = comment.getBoard();
            // 댓글 갯수 음수 방지
            board.setBoardCommentCount(board.getBoardCommentCount() + 1);
            boardRepository.save(board);
        } else {
            // 답글이 없으면 댓글 완전 삭제
            commentRepository.delete(comment);

            Board board = comment.getBoard();
            board.setBoardCommentCount(board.getBoardCommentCount() + 1);
            boardRepository.save(board);
        }
        return comment.toDTO();

    }


}



