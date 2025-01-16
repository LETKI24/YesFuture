package com.yesfuture.ex01.service;

import java.util.List;

import com.yesfuture.ex01.domain.SubReplyQuestionVO;

public interface SubReplyService {

	int createSubReplyQuestion(SubReplyQuestionVO subReplyQuestionVO);

	List<SubReplyQuestionVO> getAllSubReplyQuestion(int replyQuestionId);

}
