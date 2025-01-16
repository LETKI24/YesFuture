package com.yesfuture.ex01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yesfuture.ex01.domain.LikeQuestionVO;
import com.yesfuture.ex01.persistence.LikeQuestionMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class LikeServiceImple implements LikeService {
	
	@Autowired
	private LikeQuestionMapper likeQuestionMapper;
	
	@Override
	public int likeQuestion(LikeQuestionVO likeQuestionVO) {
		log.info("likeQuestion()");
		int insertResult = likeQuestionMapper.insertLike(likeQuestionVO);
		log.info(insertResult + "의 좋아요 추가");
		
		return insertResult;
	}

}
