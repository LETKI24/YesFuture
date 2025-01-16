package com.yesfuture.ex01.service;

import com.yesfuture.ex01.domain.LikeQuestionVO;

public interface LikeService {

	boolean likeQuestion(LikeQuestionVO likeQuestionVO);

	boolean isLikedByUser(int replyQuestionId, String memberNickname);

}
