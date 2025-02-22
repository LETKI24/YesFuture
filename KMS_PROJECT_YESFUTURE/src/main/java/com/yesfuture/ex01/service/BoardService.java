package com.yesfuture.ex01.service;

import java.util.List;

import com.yesfuture.ex01.domain.BoardFreeVO;
import com.yesfuture.ex01.domain.BoardProposeVO;
import com.yesfuture.ex01.domain.BoardQuestionVO;
import com.yesfuture.ex01.util.Pagination;

public interface BoardService {

	List<BoardQuestionVO> getBoardQuestionListMinimal();
	List<BoardProposeVO> getBoardProposeListMinimal();
	List<BoardFreeVO> getBoardFreeListMinimal();

	List<BoardQuestionVO> getQuestionPagingBoards(Pagination pagination);
	int getQuestionTotalCount();
	BoardQuestionVO getBoardQuestionById(int boardQuestionId);
	int createBoardQuestion(BoardQuestionVO boardQuestionVO);
	int updateBoardQuestion(BoardQuestionVO boardQuestionVO);
	int deleteBoardQuestion(int boardQuestionId);
	
	
	
}
