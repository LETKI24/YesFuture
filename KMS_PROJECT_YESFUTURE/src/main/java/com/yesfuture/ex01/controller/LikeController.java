package com.yesfuture.ex01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yesfuture.ex01.domain.LikeQuestionVO;
import com.yesfuture.ex01.service.LikeService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value = "/like") // url : /ex01/like
@Log4j
public class LikeController {
	
	@Autowired
	private LikeService likeService;

	@PostMapping("/replyLike") // POST : 댓글 좋아요
	public ResponseEntity<Integer> likeReplyQuestion(@RequestBody LikeQuestionVO likeQuestionVO) {
		// JSON으로 전송된 replyQuestionVO가 여기 온다-@RequestBody ReplyVO replyVO
		log.info("likeReplyQuestion()");

		int result = likeService.likeQuestion(likeQuestionVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}
