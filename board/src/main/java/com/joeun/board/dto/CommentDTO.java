package com.joeun.board.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDTO {
    private int id;    // 댓글의 아이디 값
    private String commentWriter;
    private String commentContents;
    private int boardId;
    private Date commentCreatedTime;
}
