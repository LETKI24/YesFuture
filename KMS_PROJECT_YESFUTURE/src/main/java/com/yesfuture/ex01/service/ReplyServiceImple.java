package com.yesfuture.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yesfuture.ex01.domain.ReplyQuestionVO;
import com.yesfuture.ex01.persistence.BoardQuestionMapper;
import com.yesfuture.ex01.persistence.ReplyQuestionMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyServiceImple implements ReplyService {

	@Autowired
	private ReplyQuestionMapper replyQuestionMapper;
	
	@Autowired
	private BoardQuestionMapper boardQuestionMapper;
	
	@Transactional(value = "transactionManager") 
	// transactionManager가 관리 (create랑 delete에만 쓴다)(문제가 생겼을 때 롤백해주는 기능)
	// 댓글 추가라는게 게시글(댓글갯수) 에도 영향을 줄 때 Transaction을 쓴다.
	// 만드는 기능에 따라 DB를 만들어라

	@Override
	public int createReplyQuestion(ReplyQuestionVO replyQuestionVO) {
		// 댓글을 추가하면 REPLY 테이블에 댓글(데이터)이 등록되고,
		// BOARD 테이블에 댓글수(REPLY_COUNT)가 수정된다.(= 1 추가된다.)
		// 2024-12-09, 아래 int updateResult행 추가
		log.info("createReplyQuestion()");
		int insertResult = replyQuestionMapper.insert(replyQuestionVO);
		log.info(insertResult + "행 댓글 추가");
		
		int updateResult = boardQuestionMapper.updateReplyCount(replyQuestionVO.getBoardQuestionId(), 1);
		log.info(updateResult + "행 게시판 수정");
		return 1;
	}

	@Override
	public List<ReplyQuestionVO> getAllReplyQuestion(int boardQuestionId) {
		log.info("getAllReplyQuestion()");
		return replyQuestionMapper.selectListByBoardId(boardQuestionId);
	}

	@Override // 코드 삭제해야될 것
	public int deleteReplyQuestion(int replyQuestionId, int boardQuestionId, String replyQuestionContent) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	
	
	
//	@Transactional(value = "transactionManager") // transactionManager가 관리
//	@Override
//	public int deleteReplyQuestion(int replyQuestionId, int boardQuestionId, String replyQuestionContent) {
//		log.info("deleteReply()");
//		ReplyQuestionVO replyQuestionVO = new ReplyQuestionVO();
//		int deleteResult = replyQuestionMapper.delete(replyId);
//		log.info(deleteResult + "행 댓글 삭제");
//		
//		int updateResult = boardMapper
//				.updateReplyCount(boardId, -1);
//		log.info(updateResult + "행 게시판 수정");
//		return 1;
//	}


}
