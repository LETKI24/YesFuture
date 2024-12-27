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
public class BoardProposeVO {
	private int boardProposeId;
	private String boardProposeTitle;
	private String boardProposeContent;
	private String memberNickname;
	private int memberId;
	private Date boardProposeDateCreated;
	private int boardProposeReplyCount;
	private int boardProposeHitCount;
}
