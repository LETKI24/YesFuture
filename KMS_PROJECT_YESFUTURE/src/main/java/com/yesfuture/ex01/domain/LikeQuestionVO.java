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
public class LikeQuestionVO {
	private int likeQuestionId;
	private int replyQuestionId;
	private String memberNickname;
	private Date likeQuestionDateCreated;
	private int likeQuestionType;
}
