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
public class BoardQuestion {
	private int boardQuestionId;
	private String boardQuestionTitle;
	private String boardQuestionContent;
	private String memberNickname;
	private int memberId;
	private Date boardQuestionDateCreated;
	private int boardQuestionReplyCount;
	private int boardQuestionHitCount;
	
}
