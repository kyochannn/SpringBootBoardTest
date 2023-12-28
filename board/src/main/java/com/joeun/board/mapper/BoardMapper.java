package com.joeun.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import com.joeun.board.dto.Board;

@Mapper
public interface BoardMapper {

    // 게시글 목록
    public List<Board> list() throws Exception;

    // 게시글 조회
    public Board select(int boardNo) throws Exception;

    // 게시글 등록
    public int insert(Board board) throws Exception;

    // 게시글 수정
    public int update(Board board) throws Exception;

    // 게시글 삭제
    public int delete(int boardNo) throws Exception;

    // 게시글 번호(기본키) 최댓값
    public int maxPk() throws Exception;

    // 페이징 처리 관련
    @Select("SELECT * FROM board ORDER BY id DESC LIMIT #{start}, #{pageLimit}")
    List<Board> listPage(@Param("start") int start, @Param("pageLimit") int pageLimit);

}
