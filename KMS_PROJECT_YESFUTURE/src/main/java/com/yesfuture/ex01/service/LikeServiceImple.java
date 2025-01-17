package com.yesfuture.ex01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yesfuture.ex01.domain.LikeQuestionVO;
import com.yesfuture.ex01.domain.SubLikeQuestionVO;
import com.yesfuture.ex01.persistence.LikeQuestionMapper;
import com.yesfuture.ex01.persistence.ReplyQuestionMapper;
import com.yesfuture.ex01.persistence.SubLikeQuestionMapper;
import com.yesfuture.ex01.persistence.SubReplyQuestionMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class LikeServiceImple implements LikeService {
	
	@Autowired
	private LikeQuestionMapper likeQuestionMapper;
	
	@Autowired
	private ReplyQuestionMapper replyQuestionMapper;
	
	@Autowired
	private SubLikeQuestionMapper subLikeQuestionMapper;
	
	@Autowired
	private SubReplyQuestionMapper SubReplyQuestionMapper;
	
	// 댓글(reply) 관련 기능
	
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
	
	
	// 대댓글(subReply) 관련 기능
	
	@Transactional(value = "transactionManager")
	@Override
	public boolean subLikeQuestion(SubLikeQuestionVO subLikeQuestionVO) {
		log.info("subLikeQuestion()");
		int subReplyQuestionId = subLikeQuestionVO.getSubReplyQuestionId();
		String memberNickname = subLikeQuestionVO.getMemberNickname();
		
		int count = subLikeQuestionMapper.checkLikeExist(subReplyQuestionId, memberNickname);
		
		log.info("count : " + count);
		
		if (count > 0) {
            // 이미 좋아요를 눌렀다면 삭제 (좋아요 취소)
			subLikeQuestionMapper.cancelLike(subLikeQuestionVO);
			SubReplyQuestionMapper.updateLikeCount(subLikeQuestionVO.getSubReplyQuestionId(), -1);
            return false; // 좋아요 취소
        } else {
            // 좋아요 추가
        	subLikeQuestionMapper.insertLike(subLikeQuestionVO);
        	SubReplyQuestionMapper.updateLikeCount(subLikeQuestionVO.getSubReplyQuestionId(), 1);
            return true; // 좋아요 성공
        }
	}
	
	@Override
	public boolean subLikedByUser(int subReplyQuestionId, String memberNickname) {
		
		int count = subLikeQuestionMapper.checkLikeExist(subReplyQuestionId, memberNickname);
		
		if (count > 0) {
			return true;
		} else {
			return false;
		}
		
	}
	
}
