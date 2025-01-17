package com.yesfuture.ex01.service;

import com.yesfuture.ex01.domain.LikeQuestionVO;
import com.yesfuture.ex01.domain.SubLikeQuestionVO;

public interface LikeService {

	boolean likeQuestion(LikeQuestionVO likeQuestionVO);

	boolean isLikedByUser(int replyQuestionId, String memberNickname);

	boolean subLikeQuestion(SubLikeQuestionVO subLikeQuestionVO);

	boolean subLikedByUser(int subReplyQuestionId, String memberNickname);

}
