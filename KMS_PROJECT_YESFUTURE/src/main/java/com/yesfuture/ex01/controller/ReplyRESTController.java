package com.yesfuture.ex01.controller;

import java.security.Principal;
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
import com.yesfuture.ex01.domain.ReplyQuestionVO;
import com.yesfuture.ex01.service.LikeService;
import com.yesfuture.ex01.service.ReplyService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/reply") // url : /ex01/reply
@Log4j
public class ReplyRESTController {

	@Autowired
	private ReplyService replyService;
	
	@Autowired
	private LikeService likeService;

	@PreAuthorize("principal.member.memberNickname == #replyQuestionVO.memberNickname")
	@PostMapping("/replyQuestion") // POST : 댓글 입력
	public ResponseEntity<Integer> createReplyQuestion(@RequestBody ReplyQuestionVO replyQuestionVO) {
		// JSON으로 전송된 replyQuestionVO가 여기 온다-@RequestBody ReplyVO replyVO
		log.info("createReplyQuestion()");

		int result = replyService.createReplyQuestion(replyQuestionVO);	
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/allQuestion/{boardQuestionId}") // GET : 댓글 선택(all)
	public ResponseEntity<List<ReplyQuestionVO>> readAllReplyQuestion
						(@PathVariable("boardQuestionId") int boardQuestionId) {
		// @PathVariable("boardQuestionId") : {boardQuestionId} 값을 설정된 변수에 저장
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    CustomUser customUesr = (CustomUser) authentication.getPrincipal();
	    Member member = customUesr.getMember(); // 현재 로그인한 사용자 객체
		
	    log.info("로그인한 사용자 닉네임: " + member.getMemberNickname());
	    
		List<ReplyQuestionVO> list = replyService.getAllReplyQuestion(boardQuestionId);
		
		
		for (ReplyQuestionVO reply : list) {
		    boolean isLiked = likeService.isLikedByUser(reply.getReplyQuestionId(), member.getMemberNickname());
		    reply.setLikedByCurrentUser(isLiked);
		}
		// ResponseEntity<T> : T의 타입은 프론트 side로 전송될 데이터의 타입으로 선언
		return new ResponseEntity<List<ReplyQuestionVO>>(list, HttpStatus.OK);
	}
	
	@PreAuthorize("principal.member.memberNickname == #replyQuestionVO.memberNickname")
	@PutMapping("/{replyQuestionId}") // PUT : 댓글 수정
	public ResponseEntity<Integer> updateReply(@PathVariable("replyQuestionId") int replyQuestionId
			, @RequestBody ReplyQuestionVO replyQuestionVO) {
		log.info("replyQuestionId = " + replyQuestionId);
		log.info("replyQuestionVO.get = " + replyQuestionVO.getReplyQuestionContent());
		
		int result = replyService.updateReplyQuestion(replyQuestionId, replyQuestionVO.getReplyQuestionContent());
		
	return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("principal.member.memberNickname == #memberNickname")
	@PutMapping("/deleteQuestion/{replyQuestionId}") // DELETE : 댓글 삭제 
	public ResponseEntity<Integer> deleteReplyQuestion(@PathVariable("replyQuestionId") int replyQuestionId,
			 @RequestBody String memberNickname) {
		log.info("deleteReply()");
		log.info("replyQuestionId = " + replyQuestionId);

		int result = replyService.deleteReplyQuestion(replyQuestionId);

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
}
