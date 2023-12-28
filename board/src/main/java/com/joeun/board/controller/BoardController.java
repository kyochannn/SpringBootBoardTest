package com.joeun.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joeun.board.dto.Board;
import com.joeun.board.dto.CommentDTO;
import com.joeun.board.dto.Files;
import com.joeun.board.service.BoardService;
import com.joeun.board.service.CommentService;
import com.joeun.board.service.FileService;

import lombok.extern.slf4j.Slf4j;

/**
 * 게시판 컨트롤러
 * - 게시글 목록 - [GET] - /board/list
 * - 게시글 조회 - [GET] - /board/read
 * - 게시글 등록 - [GET] - /board/insert
 * - 게시글 등록 처리 - [POST] - /board/insert
 * - 게시글 수정 - [GET] - /board/update
 * - 게시글 수정 처리 - [POST] - /board/update
 * - 게시글 삭제 처리 - [POST] - /board/delete
 */
@Slf4j // 로그 사용 어노테이션
@Controller
@RequestMapping("/board")
public class BoardController {

    // 한꺼번에 import : alt + shift + O

    @Autowired
    private BoardService boardService;

    @Autowired
    private FileService fileService;

    @Autowired
    private CommentService commentService;

    /**
     * 게시글 목록
     * [GET]
     * /board/list
     * model : boardList
     * 
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/list")
    public String list(Model model) throws Exception {
        log.info("[GET] - /board/list");

        // 데이터 요청
        List<Board> boardList = boardService.list();
        // 모델 등록
        model.addAttribute("boardList", boardList);
        // 뷰 페이지 지정
        return "board/list";
    }

    /**
     * 게시글 조회
     * [GET]
     * /board/read
     * - model : board, fileList
     * 
     * @param model
     * @param boardNo
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/read")
    public String read(Model model, int boardNo, Files files) throws Exception {
        log.info("[GET] - /board/read");

        // 데이터 요청
        Board board = boardService.select(boardNo); // 게시글 정보

        files.setParentTable("board");
        files.setParentNo(boardNo);
        List<Files> fileList = fileService.listByParent(files); // 파일 정보
        // 댓글 목록 가져옴
        List<CommentDTO> commentDTOList = commentService.findAll(boardNo);
        // 여기부터 여기 에려 발생 원인은 boardNo의 타입이 Long이기 떄문이다.

        // 모델 등록
        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", board);
        model.addAttribute("fileList", fileList);
        // 뷰 페이지 지정
        return "board/read";
    }

    /**
     * 게시글 쓰기
     * [GET]
     * /board/insert
     * model : ❌
     * 
     * @return
     */
    @GetMapping(value = "/insert")
    public String insert(@ModelAttribute Board board) {
        return "board/insert";
    }

    /**
     * 게시글 쓰기 처리
     * [POST]
     * /board/insert
     * model : ❌
     * 
     * @param board
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/insert")
    public String insertPro(@ModelAttribute Board board) throws Exception {
        // @ModelAttribute : 모델에 자동으로 등록해주는 어노테이션
        // 데이터 처리
        int result = boardService.insert(board);

        // 게시글 쓰기 실패 ➡ 게시글 쓰기 화면
        if (result == 0)
            return "board/insert";

        // 뷰 페이지 지정
        return "redirect:/board/list";
    }

    /**
     * 게시글 수정
     * [GET]
     * /board/update
     * model : board
     * 
     * @param model
     * @param boardNo
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/update")
    public String update(Model model, int boardNo) throws Exception {
        // 데이터 요청
        Board board = boardService.select(boardNo);
        // 모델 등록
        model.addAttribute("board", board);
        // 뷰 페이지 지정
        return "board/update";
    }

    /**
     * 게시글 수정 처리
     * [POST]
     * /board/update
     * model : board
     * 
     * @param board
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/update")
    public String updatePro(Board board) throws Exception {
        // 데이터 처리
        int result = boardService.update(board);
        System.out.println("BoardContorller에서 updatePro의 result : " + result);
        
        int boardNo = board.getBoardNo();
        int likes = board.getLikes();
        System.out.println("BoardContorller에서 boardNo : " + boardNo);
        System.out.println("BoardContorller에서 likes : " + likes);

        // if ( likes = null &&  )

        // 게시글 수정 실패 ➡ 게시글 수정 화면
        // if (result == 0)
            // return "redirect:/board/update?boardNo=" + boardNo;

        // 뷰 페이지 지정
        return "redirect:/board/list";
    }

    /**
     * 게시글 삭제 처리
     * [POST]
     * /board/delete
     * model : ❌
     * 
     * @param boardNo
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/delete")
    public String deletePro(int boardNo) throws Exception {
        // 데이터 처리
        int result = boardService.delete(boardNo);

        // 게시글 삭제 실패 ➡ 게시글 수정 화면
        if (result == 0)
            return "redirect:/board/update?boardNo=" + boardNo;

        // 뷰 페이지 지정
        return "redirect:/board/list";
    }

    /**
     * @param model
     * @param page
     * @param pageLimit
     * @return
     */
    // @GetMapping("/paging")
    // public String list(Model model,
    //         @PageableDefault(size = 10) Pageable pageable) throws Exception {
    //     // 데이터 요청
    //     Page<Board> boardPage = boardService.list(pageable);
    //     // 모델 등록
    //     model.addAttribute("boardPage", boardPage);
    //     return "board/paging";
    // }
}