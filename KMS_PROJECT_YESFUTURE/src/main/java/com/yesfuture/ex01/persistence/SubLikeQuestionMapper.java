package com.yesfuture.ex01.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yesfuture.ex01.domain.SubLikeQuestionVO;

@Mapper
public interface SubLikeQuestionMapper {

	void insertLike(SubLikeQuestionVO subLikeQuestionVO);

	void cancelLike(SubLikeQuestionVO subLikeQuestionVO);

	int checkLikeExist(@Param("subReplyQuestionId") int replyQuestionId, @Param("memberNickname") String memberNickname);

}
