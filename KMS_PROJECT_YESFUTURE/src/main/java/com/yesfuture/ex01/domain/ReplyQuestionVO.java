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
public class ReplyQuestionVO {
	private int replyQuestionId;
	private int boardQuestionId;
	private String memberNickname;
	private String memberId;
	private String replyQuestionContent;
	private Date replyQuestionDateCreated;
	private int replyQuestionLike;
	private int replyQuestionHate;
	private int replyQuestionEnabled;
	
    // 좋아요 여부를 저장하는 필드 추가
    private boolean likedByCurrentUser;
	
}

