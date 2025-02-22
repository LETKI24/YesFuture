package com.mokcoding.ex03.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mokcoding.ex03.domain.ReplyVO;
import com.mokcoding.ex03.persistence.BoardMapper;
import com.mokcoding.ex03.persistence.ReplyMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyServiceImple implements ReplyService {
	
	@Autowired
	private ReplyMapper replyMapper;
	
	@Autowired
	private BoardMapper boardMapper;
	
	// 2024-12-10추가
	@Transactional(value = "transactionalManager") 
	// transactionManager가 관리 (create랑 delete에만 쓴다)(문제가 생겼을 때 롤백해주는 기능)
	// 댓글 추가라는게 게시글(댓글갯수) 에도 영향을 줄 때 Transaction을 쓴다.
	// 만드는 기능에 따라 DB를 만들어라
	
	@Override
	public int createReply(ReplyVO replyVO) {
		// 댓글을 추가하면 REPLY 테이블에 댓글(데이터)이 등록되고,
		// BOARD 테이블에 댓글수(REPLY_COUNT)가 수정된다.(= 1 추가된다.)
		// 2024-12-09, 아래 int updateResult행 추가
		log.info("createReply()");
		int insertResult = replyMapper.insert(replyVO);
		log.info(insertResult + "행 댓글 추가");
		
		int updateResult = boardMapper
				.updateReplyCount(replyVO.getBoardId(), 1);
		log.info(updateResult + "행 게시판 수정");
		return 1;
	}

	@Override
	public List<ReplyVO> getAllReply(int boardId) {
		log.info("getAllReply()");
		return replyMapper.selectListByBoardId(boardId);
	}

	@Override
	public int updateReply(int replyId, String replyContent) {
		log.info("updateReply");
		ReplyVO replyVO = new ReplyVO();
		replyVO.setReplyId(replyId);
		replyVO.setReplyContent(replyContent);
		return replyMapper.update(replyVO);
	}

	// 2024-12-10추가
	@Transactional(value = "transactionalManager") // transactionManager가 관리
	
	@Override
	public int deleteReply(int replyId, int boardId) {
		log.info("deleteReply()");
		int deleteResult = replyMapper.delete(replyId);
		log.info(deleteResult + "행 댓글 삭제");
		
		int updateResult = boardMapper
				.updateReplyCount(boardId, -1);
		log.info(updateResult + "행 게시판 수정");
		return 1;
	}

}
