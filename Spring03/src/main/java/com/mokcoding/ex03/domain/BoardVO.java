package com.mokcoding.ex03.domain;

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
public class BoardVO {
	private int boardId; // BOARD_ID
	private String boardTitle; // BOARD_TITLE
	private String boardContent; // BOARD_CONTENT
	private String memberId; // MEMBER_ID
	private Date boardDateCreated; // BOARD_DATE_CREATED
	private int replyCount; // REPLY_COUNT
}
