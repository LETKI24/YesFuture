package com.mokcoding.ex02.service;

import java.util.List;

import com.mokcoding.ex02.domain.BoardVO;
import com.mokcoding.ex02.util.Pagination;

public interface BoardService { // DB쪽보다는 front side이기 때문에 명명법이 약간 다르다. 
	// (ex : DB쪽은 insert, 여기는 create)
	int createBoard(BoardVO boardVO);
	List<BoardVO> getAllBoards();
	BoardVO getBoardById(int boardId);
	int updateBoard(BoardVO boardVO);
	int deleteBoard(int boardId);
	List<BoardVO> getPagingBoards(Pagination pagination);
	int getTotalCount();
}
