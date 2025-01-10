package com.yesfuture.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yesfuture.ex01.domain.BoardQuestion;
import com.yesfuture.ex01.domain.BoardQuestionVO;
import com.yesfuture.ex01.util.Pagination;

@Mapper
public interface BoardQuestionMapper {
	List<BoardQuestionVO> getBoardQuestionListMinimal();
	
	List<BoardQuestionVO> selectQuestionListByPagination(Pagination pagination);
	int selectQuestionTotalCount();
	
	BoardQuestion selectOne(int boardQuestionId);
	int insert(BoardQuestion boardQuestion);
	int update(BoardQuestion boardQuestion);
	int delete(int boardQuestionId);
	
	int updateReplyCount (@Param("boardQuestionId") int boardQuestionId, @Param("amount") int amount); 
	// 댓글 수 변경
	// 이 코딩만으로는 BoardMapper.xml의 애들과 매핑 안됨, 에러남
	// @Param : 자바 객체의 속성을 Mapper에 매핑 / 각각 @Param("boardId"), @Param("amount") 추가함 
}
