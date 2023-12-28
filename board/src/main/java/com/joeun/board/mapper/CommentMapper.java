package com.joeun.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.joeun.board.dto.CommentDTO;

@Mapper
public interface CommentMapper {
    int insertComment(CommentDTO comment);
    List<CommentDTO> selectCommentsByBoardId(int boardId);
}