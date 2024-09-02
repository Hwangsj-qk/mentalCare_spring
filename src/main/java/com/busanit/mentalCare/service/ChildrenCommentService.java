package com.busanit.mentalCare.service;

import com.busanit.mentalCare.dto.ChildrenCommentDTO;
import com.busanit.mentalCare.entity.Board;
import com.busanit.mentalCare.entity.ChildrenComment;
import com.busanit.mentalCare.entity.Comment;
import com.busanit.mentalCare.entity.User;
import com.busanit.mentalCare.repository.BoardRepository;
import com.busanit.mentalCare.repository.ChildrenCommentRepository;
import com.busanit.mentalCare.repository.CommentRepository;
import com.busanit.mentalCare.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildrenCommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChildrenCommentRepository childrenRepository;

    @PersistenceContext
    private EntityManager entityManager;



    // 엔티티 -> DTO로 변환하여 전달
    public List<ChildrenCommentDTO> getAllChildren() {
        List<ChildrenComment> children = childrenRepository.findAll();
        return children.stream().map(ChildrenComment::toDTO).toList();
    }

    @Transactional
    public List<ChildrenCommentDTO> getChildrenByCommentId(Long commentId) {
        List<ChildrenComment> childrenList = childrenRepository.findByCommentCommentId(commentId);
        return childrenList.stream().map(ChildrenComment::toDTO).toList();
    }

    @Transactional
    public ChildrenCommentDTO createChildren(ChildrenCommentDTO dto) {
        User user = userRepository.findByUserNickname(dto.getUserNickname());
        Comment comment = commentRepository.findById(dto.getCommentId())
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않는 답글"));

        // 답글 엔티티 생성 및 저장
        ChildrenComment children = dto.toEntity(comment, user);
        ChildrenComment saved = childrenRepository.save(children);

        // Board 엔티티를 다시 로드하여 CommentCount 증가
        Board board = comment.getBoard();
        board.setBoardCommentCount(board.getBoardCommentCount() + 1);
        boardRepository.save(board);

        return saved.toDTO();
    }

    @Transactional
    public ChildrenCommentDTO updateChildren(Long childrenId, ChildrenCommentDTO updateChildren) {
        ChildrenComment children = childrenRepository.findById(childrenId).orElse(null);

        if(children != null) {
            if(updateChildren.getChildrenContent() != null) {
                children.setChildrenContent(updateChildren.getChildrenContent());
            }

            ChildrenComment saved = childrenRepository.save(children);
            return saved.toDTO();
        } else {
            return null;
        }
    }

    @Transactional
    public ChildrenCommentDTO deleteChildrenComment(Long childrenCommentId) {
        // 답글 조회
        ChildrenComment childrenComment = childrenRepository.findById(childrenCommentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 답글이 존재하지 않습니다."));

        // 부모 댓글 가져오기
        Comment parentComment = childrenComment.getComment();

        // 답글 삭제
        childrenRepository.delete(childrenComment);

        entityManager.flush(); // 영속성 컨텍스트 강제 플러시
        entityManager.refresh(parentComment); // 부모 댓글의 상태를 다시 로딩

        // 부모 댓글의 남아 있는 답글 개수 확인
        List<ChildrenComment> remainingChildrenComments = parentComment.getChildrenComments();

        if (remainingChildrenComments.isEmpty() && "삭제된 댓글입니다.".equals(parentComment.getCommentContent())) {
            // 남아있는 답글이 없고, 부모 댓글이 "삭제된 댓글입니다."인 경우 부모 댓글도 삭제
            commentRepository.delete(parentComment);

            // board의 댓글 수 감소
            Board board = parentComment.getBoard();
            board.setBoardCommentCount(board.getBoardCommentCount() - 1);
            boardRepository.save(board);
        } else {
            // 남아있는 답글이 있는 경우 부모 댓글은 그대로 두고 board의 댓글 수만 감소
            Board board = parentComment.getBoard();
            board.setBoardCommentCount(board.getBoardCommentCount() - 1);
            boardRepository.save(board);
        }
        return childrenComment.toDTO();
    }

}
