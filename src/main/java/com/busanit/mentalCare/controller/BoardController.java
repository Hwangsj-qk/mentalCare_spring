package com.busanit.mentalCare.controller;

import com.busanit.mentalCare.dto.BoardDTO;
import com.busanit.mentalCare.model.TagType;
import com.busanit.mentalCare.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity<BoardDTO> createBoard(@RequestBody BoardDTO board) {
        BoardDTO createdBoard = boardService.createBoard(board);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBoard);
    }

    @GetMapping("/TagType/{tagType}")
    public List<BoardDTO> getBoardByTagType(@PathVariable TagType tagType) {
        return boardService.getBoardByTagType(tagType);
    }

    @GetMapping("/AllBoards")
    public ResponseEntity<List<BoardDTO>> getAllBoards() {
        List<BoardDTO> boards = boardService.getAllBoards();
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/BoardId/{boardId}")
    public ResponseEntity<BoardDTO> getBoardById(@PathVariable Long boardId) {
        BoardDTO board= boardService.getBoardById(boardId);
        if(board == null ) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(board);
        }
    }

    @PutMapping("/update/{boardId}")
    public ResponseEntity<BoardDTO> updateBoard(@PathVariable Long boardId, @RequestBody BoardDTO updateBoard) {
        BoardDTO board = boardService.updateBoard(boardId, updateBoard);
        if(board == null) {
            return ResponseEntity.notFound().build();
        }
            return ResponseEntity.ok(board);
    }

    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<BoardDTO> deleteBoard(@PathVariable Long boardId) {
        BoardDTO boardDTO = boardService.DeleteBoard(boardId);
        if(boardDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(boardDTO);
    }

}
