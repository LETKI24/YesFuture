package com.yesfuture.ex01.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString 
public class Member implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int memberId;
	private String memberName;
	private String memberNickname;
	private String memberEmail;
	private String memberPw;
	private String memberSchool;
	private String memberPhone;
	private Date memberDateCreated; // 회원 가입 날짜
	private int memberSelected; // 질문 게시판에서 채택당한 횟수
	private int memberEnabled; // 회원 계정 활성화, 비활성화 상태
}
