package com.yesfuture.ex01.persistence;

import org.apache.ibatis.annotations.Param;

import com.yesfuture.ex01.domain.LikeQuestionVO;

public interface LikeQuestionMapper {

	int insertLike(LikeQuestionVO likeQuestionVO);
	
	
	int cancelLike(LikeQuestionVO likeQuestionVO);
	int checkLikeExist(@Param("replyQuestionId") int replyQuestionId, @Param("memberNickname") String memberNickname);
}
