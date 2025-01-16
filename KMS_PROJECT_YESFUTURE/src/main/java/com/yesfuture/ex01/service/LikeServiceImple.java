package com.yesfuture.ex01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yesfuture.ex01.domain.LikeQuestionVO;
import com.yesfuture.ex01.persistence.LikeQuestionMapper;
import com.yesfuture.ex01.persistence.ReplyQuestionMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class LikeServiceImple implements LikeService {
	
	@Autowired
	private LikeQuestionMapper likeQuestionMapper;
	
	@Autowired
	private ReplyQuestionMapper replyQuestionMapper;
	
	@Transactional(value = "transactionManager") 
	@Override
	public boolean likeQuestion(LikeQuestionVO likeQuestionVO) {
		log.info("likeQuestion()");
		int replyQuestionId = likeQuestionVO.getReplyQuestionId();
		String memberNickname = likeQuestionVO.getMemberNickname();
		
		int count = likeQuestionMapper.checkLikeExist(replyQuestionId, memberNickname);
		
		log.info("count : " + count);
		
		if (count > 0) {
            // 이미 좋아요를 눌렀다면 삭제 (좋아요 취소)
			likeQuestionMapper.cancelLike(likeQuestionVO);
			replyQuestionMapper.updateLikeCount(likeQuestionVO.getReplyQuestionId(), -1);
            return false; // 좋아요 취소
        } else {
            // 좋아요 추가
        	likeQuestionMapper.insertLike(likeQuestionVO);
        	replyQuestionMapper.updateLikeCount(likeQuestionVO.getReplyQuestionId(), 1);
            return true; // 좋아요 성공
        }
	}

	@Override
	public boolean isLikedByUser(int replyQuestionId, String memberNickname) {
		
		int count = likeQuestionMapper.checkLikeExist(replyQuestionId, memberNickname);
		
		if (count > 0) {
			return true;
		} else {
			return false;
		}
		
	}

}
