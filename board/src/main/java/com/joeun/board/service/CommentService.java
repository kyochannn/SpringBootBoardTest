package com.joeun.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joeun.board.dto.CommentDTO;
import com.joeun.board.mapper.CommentMapper;

@Service
public class CommentService {
    private final CommentMapper commentMapper;

    public CommentService(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    // 댓글 쓰기
    public int save(CommentDTO commentDTO) {
        return commentMapper.insertComment(commentDTO);
    }

    public List<CommentDTO> findAll(int boardId) {
        return commentMapper.selectCommentsByBoardId(boardId);
    }
}

