package com.busanit.mentalCare.service;

import com.busanit.mentalCare.entity.Board;
import com.busanit.mentalCare.entity.Heart;
import com.busanit.mentalCare.entity.User;
import com.busanit.mentalCare.repository.BoardRepository;
import com.busanit.mentalCare.repository.HeartRepository;
import com.busanit.mentalCare.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class HeartService {

    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;

    public Integer addHeart(Long boardId, User user) {
        // 공감을 추가할 게시판(Board)의 ID
        Board board = boardService.findBoardId(boardId);
        // 공감을 추가한 대상자(User)의 ID
        User findUser = userRepository.findById(user.getUserId()).orElse(null);

        // existsByUseAndBoard 메소드를 통해 해당 게시물에 대상자가 이미 공감을 했는지 여부 검토
        if(!heartRepository.existsByUserAndBoard(findUser, board)) {
            // 대상자가 해당 게시판에 공감버튼을 누른 적 없다면 board에 있는 boardHeartCount 증가
            board.setBoardHeartCount(board.getBoardHeartCount()+1);

            // heartRepository에 userId랑 boardId값 저장
            heartRepository.save(new Heart(user, board));
            return board.getBoardHeartCount();
        } else {
            // 대상자가 해당 게시판에 이미 공감버튼을 눌렀다면 공감 취소
            board.setBoardHeartCount(board.getBoardHeartCount()-1);
            heartRepository.deleteByUserAndBoard(user, board);
            return board.getBoardHeartCount();
        }
    }


}
