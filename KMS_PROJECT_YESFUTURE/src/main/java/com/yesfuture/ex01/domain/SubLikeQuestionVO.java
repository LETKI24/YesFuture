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
public class SubLikeQuestionVO {
	private int subLikeQuestionId;
	private int subReplyQuestionId;
	private String memberNickname;
	private Date subLikeQuestionDateCreated;
	private int subLikeQuestionType;
}
