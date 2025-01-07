package com.mokcoding.mysite.service;

import java.util.List;

import com.mokcoding.mysite.domain.ReplyDTO;

public interface ReplyService {
	int createReply(ReplyDTO replyDTO);
	List<ReplyDTO> getAllReply(int boardId);
	int updateReply(int replyId, String replyContent);
	int deleteReply(int replyId, int boardId);
} // end ReplyService
