package com.yesfuture.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yesfuture.ex01.domain.ReplyQuestionVO;

@Mapper
public interface ReplyQuestionMapper {

	int insert(ReplyQuestionVO replyQuestionVO);

	List<ReplyQuestionVO> selectListByBoardId(int boardQuestionId);

	


}
