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
public class MemberVO {
	private int memberId;
	private String memberName;
	private String memberNickname;
	private String memberEmail;
	private String memberPw;
	private String memberSchool;
	private String memberPhone;
	private Date memberDateCreated; // 회원 가입 날짜
	private int memberSelected; // 질문 게시판에서 채택당한 횟수
	private int memberEnabled; // 1은 활성계정, 2는 비활성계정(=회원탈퇴)
	
	
	// 당장 안쓸거, 회원 생일, 소속병원
//	private Date userBirthday;
//	private String memberHospital;
}
