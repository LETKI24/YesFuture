package com.yesfuture.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yesfuture.ex01.domain.SubReplyQuestionVO;
import com.yesfuture.ex01.persistence.SubReplyQuestionMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class SubReplyServiceImple implements SubReplyService{

	@Autowired
	private SubReplyQuestionMapper subReplyQuestionMapper;
	
	@Override
	public int createSubReplyQuestion(SubReplyQuestionVO subReplyQuestionVO) {
		log.info("createReplyQuestion()");
		int insertResult = subReplyQuestionMapper.insert(subReplyQuestionVO);
		log.info(insertResult + "행 댓글 추가");
		
		return 1;
	}

	@Override
	public List<SubReplyQuestionVO> getAllSubReplyQuestion(int replyQuestionId) {
		log.info("getAllSubReplyQuestion()");
		return subReplyQuestionMapper.selectListByReplyId(replyQuestionId);
	}
	
}
