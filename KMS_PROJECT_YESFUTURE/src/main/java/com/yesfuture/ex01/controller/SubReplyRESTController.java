package com.yesfuture.ex01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yesfuture.ex01.domain.SubReplyQuestionVO;
import com.yesfuture.ex01.service.SubReplyService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/subReply") // url : /ex01/subReply
@Log4j
public class SubReplyRESTController {

	@Autowired
	private SubReplyService subReplyService;

	@PostMapping("/subReplyQuestion") // POST : 댓글 입력
	public ResponseEntity<Integer> createSubReplyQuestion(@RequestBody SubReplyQuestionVO subReplyQuestionVO) {
		// JSON으로 전송된 replyQuestionVO가 여기 온다-@RequestBody ReplyVO replyVO
		log.info("createReplyQuestion()");
		log.info("subReplyQuestionVO : " + subReplyQuestionVO);

		int result = subReplyService.createSubReplyQuestion(subReplyQuestionVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/allQuestion/{replyQuestionId}") // GET : 댓글 선택(all)
	public ResponseEntity<List<SubReplyQuestionVO>> readAllSubReplyQuestion
						(@PathVariable("replyQuestionId") int replyQuestionId) {
		// @PathVariable("replyQuestionId") : {replyQuestionId} 값을 설정된 변수에 저장
		
		List<SubReplyQuestionVO> list = subReplyService.getAllSubReplyQuestion(replyQuestionId);
		// ResponseEntity<T> : T의 타입은 프론트 side로 전송될 데이터의 타입으로 선언
		return new ResponseEntity<List<SubReplyQuestionVO>>(list, HttpStatus.OK);
	}
	
}