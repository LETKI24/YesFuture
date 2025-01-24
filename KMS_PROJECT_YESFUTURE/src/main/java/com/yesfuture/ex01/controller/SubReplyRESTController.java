package com.yesfuture.ex01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yesfuture.ex01.domain.CustomUser;
import com.yesfuture.ex01.domain.Member;
import com.yesfuture.ex01.domain.SubReplyQuestionVO;
import com.yesfuture.ex01.service.LikeService;
import com.yesfuture.ex01.service.SubReplyService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/subReply") // url : /ex01/subReply
@Log4j
public class SubReplyRESTController {

	@Autowired
	private SubReplyService subReplyService;
	
	@Autowired
	private LikeService likeService;

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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    CustomUser customUesr = (CustomUser) authentication.getPrincipal();
	    Member member = customUesr.getMember(); // 현재 로그인한 사용자 객체
		
	    log.info("sub-로그인한 사용자 닉네임: " + member.getMemberNickname());
	    
		List<SubReplyQuestionVO> list = subReplyService.getAllSubReplyQuestion(replyQuestionId);
		
		for (SubReplyQuestionVO subReply : list) {
		    boolean isLiked = likeService.subLikedByUser(subReply.getSubReplyQuestionId(), member.getMemberNickname());
		    subReply.setSubLikedByCurrentUser(isLiked);
		}
		
		// ResponseEntity<T> : T의 타입은 프론트 side로 전송될 데이터의 타입으로 선언
		return new ResponseEntity<List<SubReplyQuestionVO>>(list, HttpStatus.OK);
	}
	
	@PreAuthorize("principal.member.memberNickname == #memberNickname")
	@PutMapping("/deleteQuestion/{subReplyQuestionId}")
	public ResponseEntity<Integer> deleteSubReplyQuestion(@PathVariable("subReplyQuestionId") int subReplyQuestionId,
			 @RequestBody String memberNickname) {
		log.info("deleteSubReplyQuestion()");
		log.info("subReplyQuestionId = " + subReplyQuestionId);

		int result = subReplyService.deleteSubReplyQuestion(subReplyQuestionId);

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
}