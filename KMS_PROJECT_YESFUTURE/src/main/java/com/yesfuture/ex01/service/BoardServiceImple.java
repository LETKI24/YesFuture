package com.yesfuture.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yesfuture.ex01.domain.AttachQuestion;
import com.yesfuture.ex01.domain.AttachQuestionVO;
import com.yesfuture.ex01.domain.BoardFreeVO;
import com.yesfuture.ex01.domain.BoardProposeVO;
import com.yesfuture.ex01.domain.BoardQuestion;
import com.yesfuture.ex01.domain.BoardQuestionVO;
import com.yesfuture.ex01.persistence.AttachQuestionMapper;
import com.yesfuture.ex01.persistence.BoardFreeMapper;
import com.yesfuture.ex01.persistence.BoardProposeMapper;
import com.yesfuture.ex01.persistence.BoardQuestionMapper;
import com.yesfuture.ex01.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BoardServiceImple implements BoardService{

	@Autowired
	private BoardQuestionMapper boardQuestionMapper;
	
	@Autowired
	private AttachQuestionMapper attachQuestionMapper;
	
	@Autowired
	private BoardProposeMapper boardProposeMapper;
	
	@Autowired
	private BoardFreeMapper boardFreeMapper;
	
	@Override
	public List<BoardQuestionVO> getBoardQuestionListMinimal() {
		log.info("getBoardQuestionListMinimal()");
		return boardQuestionMapper.getBoardQuestionListMinimal();
	}

	@Override
	public List<BoardProposeVO> getBoardProposeListMinimal() {
		log.info("getBoardProposeListMinimal()");
		return boardProposeMapper.getBoardProposeListMinimal();
	}

	@Override
	public List<BoardFreeVO> getBoardFreeListMinimal() {
		log.info("getBoardFreeListMinimal()");
		return boardFreeMapper.getBoardFreeListMinimal();
	}
	
	// 질문게시판 관련 ServiceImple
	
	@Override
	public List<BoardQuestionVO> getQuestionPagingBoards(Pagination pagination) {
		log.info("getQuestionPagingBoards()");
		return boardQuestionMapper.selectQuestionListByPagination(pagination);
	}

	@Override
	public int getQuestionTotalCount() {
		log.info("getQuestionTotalCount()");
		return boardQuestionMapper.selectQuestionTotalCount();
	}

	@Override
	public BoardQuestionVO getBoardQuestionById(Integer boardQuestionId) {
		log.info("getBoardQuestionById");
		return boardQuestionMapper.selectOne(boardQuestionId);
	}
	
	@Transactional(value = "transactionManager") 
	@Override
	public int createBoardQuestion(BoardQuestionVO boardQuestionVO) {
		log.info("createBoardQuestion()");
		int insertBoardQuestionResult = boardQuestionMapper.insert(toEntity(boardQuestionVO));
		log.info(insertBoardQuestionResult + "행 게시글 등록");
	
		List<AttachQuestionVO> attachQuestionList = boardQuestionVO.getAttachQuestionList();

		int insertAttachQuestionResult = 0;
		for(AttachQuestionVO attachQuestionVO : attachQuestionList) {
			insertAttachQuestionResult += attachQuestionMapper.insert(toEntity(attachQuestionVO));
		}
		log.info(insertAttachQuestionResult + "행 파일 정보 등록");
		
		return 1;
	}

	@Override
	public int updateBoardQuestion(BoardQuestionVO boardQuestionVO) {
		log.info("updateBoardQuestion()");
		return boardQuestionMapper.update(boardQuestionVO);
	}

	@Override
	public int deleteBoardQuestion(Integer boardQuestionId) {
		log.info("deleteBoardQuestion()");
		return boardQuestionMapper.delete(boardQuestionId);
	}
	
	// BoardQuestionVO 데이터를 BoardQuestion에 적용하는 메서드
	public BoardQuestion toEntity(BoardQuestionVO boardQuestionVO) {
		BoardQuestion boardQuestion = new BoardQuestion();
		boardQuestion.setBoardQuestionId(boardQuestionVO.getBoardQuestionId());
		boardQuestion.setBoardQuestionTitle(boardQuestionVO.getBoardQuestionTitle());
		boardQuestion.setBoardQuestionContent(boardQuestionVO.getBoardQuestionContent());
		boardQuestion.setMemberNickname(boardQuestionVO.getMemberNickname());
		boardQuestion.setMemberId(boardQuestionVO.getMemberId());
		boardQuestion.setBoardQuestionDateCreated(boardQuestionVO.getBoardQuestionDateCreated());
		boardQuestion.setBoardQuestionReplyCount(boardQuestionVO.getBoardQuestionReplyCount());
		boardQuestion.setBoardQuestionHitCount(boardQuestionVO.getBoardQuestionHitCount());
		return boardQuestion;
	} // end toEntity()
	
    // AttachQuestionVO를 AttachQuestion로 변환하는 메서드
    private AttachQuestion toEntity(AttachQuestionVO attachQuestionVO) {
        AttachQuestion attachQuestion = new AttachQuestion();
        attachQuestion.setAttachQuestionId(attachQuestionVO.getAttachQuestionId());
        attachQuestion.setBoardQuestionId(attachQuestionVO.getBoardQuestionId());
        attachQuestion.setAttachQuestionPath(attachQuestionVO.getAttachQuestionPath());
        attachQuestion.setAttachQuestionRealName(attachQuestionVO.getAttachQuestionRealName());
        attachQuestion.setAttachQuestionChgName(attachQuestionVO.getAttachQuestionChgName());
        attachQuestion.setAttachQuestionExtension(attachQuestionVO.getAttachQuestionExtension());
        attachQuestion.setAttachQuestionDateCreated(attachQuestionVO.getAttachQuestionDateCreated());
        return attachQuestion;
    }

}
