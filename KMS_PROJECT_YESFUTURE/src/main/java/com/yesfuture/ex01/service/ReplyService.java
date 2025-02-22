package com.yesfuture.ex01.service;

import java.util.List;

import com.yesfuture.ex01.domain.ReplyQuestionVO;

public interface ReplyService {

	int createReplyQuestion(ReplyQuestionVO replyQuestionVO);

	List<ReplyQuestionVO> getAllReplyQuestion(int boardQuestionId);

	int deleteReplyQuestion(int replyQuestionId);

	int updateReplyQuestion(int replyQuestionId, String replyQuestionContent);


}
