package com.yesfuture.ex01.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SubReplyQuestionVO {
	private int subReplyQuestionId;
	private int replyQuestionId;
	private String memberNickname;
	private String memberId;
	private String subReplyQuestionContent;
	private Date subReplyQuestionDateCreated;
	private int subReplyQuestionLike;
	private int subReplyQuestionHate;
	
	
    // 좋아요 여부를 저장하는 필드 추가
    private boolean subLikedByCurrentUser;
	
}