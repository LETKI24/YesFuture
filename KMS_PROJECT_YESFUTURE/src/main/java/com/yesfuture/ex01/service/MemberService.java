package com.yesfuture.ex01.service;

import com.yesfuture.ex01.domain.MemberVO;

public interface MemberService {
	int createMember(MemberVO memberVO);
	int checkMemberNickname(String memberNickname);
	int checkMemberEmail(String memberEmail);
	MemberVO verifyMember(MemberVO memberVO);
}
