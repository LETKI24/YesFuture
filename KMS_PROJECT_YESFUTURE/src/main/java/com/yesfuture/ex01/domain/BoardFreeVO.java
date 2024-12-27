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
public class BoardFreeVO {
	private int boardFreeId;
	private String boardFreeTitle;
	private String boardFreeContent;
	private String memberNickname;
	private int memberId;
	private Date boardFreeDateCreated;
	private int boardFreeReplyCount;
	private int boardFreeHitCount;
	private int boardFreeLikeCount;
}
