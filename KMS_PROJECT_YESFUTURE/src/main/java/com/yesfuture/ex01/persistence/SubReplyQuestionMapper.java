package com.yesfuture.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yesfuture.ex01.domain.SubReplyQuestionVO;

@Mapper
public interface SubReplyQuestionMapper {

	int insert(SubReplyQuestionVO subReplyQuestionVO);

	List<SubReplyQuestionVO> selectListByReplyId(int replyQuestionId);

}
