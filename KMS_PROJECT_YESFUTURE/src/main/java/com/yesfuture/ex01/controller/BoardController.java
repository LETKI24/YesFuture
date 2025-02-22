package com.yesfuture.ex01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yesfuture.ex01.domain.BoardFreeVO;
import com.yesfuture.ex01.domain.BoardProposeVO;
import com.yesfuture.ex01.domain.BoardQuestionVO;
import com.yesfuture.ex01.service.BoardService;
import com.yesfuture.ex01.util.PageMaker;
import com.yesfuture.ex01.util.Pagination;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value = "/boardReal") // url : /ex01/boardReal
@Log4j
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// boardMain.jsp 페이지 호출
	@GetMapping("/boardMain")
	public void boardMainGET(Model model) {
		log.info("boardMainGET()");
		List<BoardQuestionVO> boardQuestionListMinimal =  boardService.getBoardQuestionListMinimal();
		
		model.addAttribute("boardQuestionListMinimal", boardQuestionListMinimal);
	}
	
	// boardMain.jsp의 건의게시판 조회순 10개
	@GetMapping("/proposeMinimal")
	@ResponseBody
	public List<BoardProposeVO> getBoardProposeListMinimal() {
		log.info("getBoardProposeListMinimal()");
        List<BoardProposeVO> boardProposeListMinimal = boardService.getBoardProposeListMinimal();
        return boardProposeListMinimal; // JSON 형태로 반환
    }
	
	// boardMain.jsp의 질문게시판 조회순 10개
	@GetMapping("/questionMinimal")
	@ResponseBody
	public List<BoardQuestionVO> getBoardQuestionListMinimal() {
		log.info("getBoardQuestionListMinimal()");
        List<BoardQuestionVO> boardQuestionListMinimal = boardService.getBoardQuestionListMinimal();
        return boardQuestionListMinimal; // JSON 형태로 반환
    }
	
	// boardMain.jsp의 자유게시판 조회순 10개
	@GetMapping("/freeMinimal")
	@ResponseBody
	public List<BoardFreeVO> getBoardFreeListMinimal() {
		log.info("getBoardFreeListMinimal()");
        List<BoardFreeVO> boardFreeListMinimal = boardService.getBoardFreeListMinimal();
        return boardFreeListMinimal; // JSON 형태로 반환
    }
	
	// boardFree.jsp 페이지 호출
	@GetMapping("/boardFree")
	public void boardFreeList() {
		log.info("boardFreeList()");
	}
	
	// boardQuestion.jsp 페이지 호출
	@GetMapping("/boardQuestion/boardQuestion")
	public void boardQuestionList(Model model, Pagination pagination) {
		log.info("boardQuestionList()");
		log.info("pagination = " + pagination);
		List<BoardQuestionVO> boardQuestionList = boardService.getQuestionPagingBoards(pagination);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(boardService.getQuestionTotalCount());

		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("boardQuestionList", boardQuestionList);
	}
	
	// boardQuestion.jsp에서 선택된 게시글 번호를 바탕으로 게시글 상세 조회
	// 조회된 게시글 데이터를 boardQuestionDetail.jsp로 전송
	@GetMapping("/boardQuestion/boardQuestionDetail")
	public void boardQuestionDetail(Model model, Integer boardQuestionId) {
		log.info("boardQuestionDetail()");
		BoardQuestionVO boardQuestionVO = boardService.getBoardQuestionById(boardQuestionId);
		model.addAttribute("boardQuestionVO", boardQuestionVO);
	}
	
	// /boardQuestion/registerQuestion.jsp 페이지 호출
	@GetMapping("/boardQuestion/registerQuestion")
	public void registerQuestionGET() {
		log.info("registerQuestionGET()");
	}

	// /boardQuestion/registerQuestion.jsp에서 게시글 데이터를 form-data를 전송받아 Service로 전송
	@PostMapping("/boardQuestion/registerQuestion")
	public String registerQuestionPOST(BoardQuestionVO boardQuestionVO, RedirectAttributes reAttr) {
		log.info("registerQuestionPOST()");
		log.info("boardQuestionVO = " + boardQuestionVO.toString());
		
		int result = boardService.createBoardQuestion(boardQuestionVO);
		log.info(result + "행 등록");
		return "redirect:/boardReal/boardQuestion/boardQuestion";
	}
	
	// 조회된 게시글 데이터를 /boardQuestion/modifyQeustion.jsp로 전송
	@GetMapping("/boardQuestion/modifyQuestion")
	public void modifyQuestionGET(Model model, Integer boardQuestionId) {
		log.info("modifyQuestionGET()");
		log.info("boardQuestionId = " + boardQuestionId);
		BoardQuestionVO boardQuestionVO = boardService.getBoardQuestionById(boardQuestionId);
		model.addAttribute("boardQuestionVO", boardQuestionVO);
	}

	// /boardQuestion/modify.jsp에서 데이터를 전송받아 게시글 수정
	@PostMapping("/boardQuestion/modifyQuestion")
	public String modifyQuestionPOST(BoardQuestionVO boardQuestionVO) {
		log.info("modifyQuestionPOST()");
		int result = boardService.updateBoardQuestion(boardQuestionVO);
		log.info(result + " 행 수정");
		return "redirect:/boardReal/boardQuestion/boardQuestion";
	}
	
	// boardQuestionDetail.jsp에서 boardQuestionId를 전송받아 게시글 데이터 삭제
	@PostMapping("/boardQuestion/deleteQuestion")
	public String deleteQuestion(Integer boardQuestionId) {
		log.info("deleteQuestion()");
		int result = boardService.deleteBoardQuestion(boardQuestionId);
		log.info(result + "행 삭제");
		return "redirect:/boardReal/boardQuestion/boardQuestion";
	}
	
	
	// boardPropose.jsp 페이지 호출
	@GetMapping("/boardPropose")
	public void boardProposeList() {
		log.info("boardProposeList()");
	}
	
}
