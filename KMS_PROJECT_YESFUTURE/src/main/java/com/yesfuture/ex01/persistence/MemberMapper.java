package com.yesfuture.ex01.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.yesfuture.ex01.domain.MemberVO;

@Mapper
public interface MemberMapper {
	// 메소드 이름은 MemberMapper.xml에서 SQL 쿼리 정의 태그의 id 값과 동일
	// 매개변수(MemberVO)는 MemeberMapper.xml에서 #{변수명}과 동일(클래스 타입은 각 멤버변수명과 매칭)
	int insert(MemberVO memberVO); // 게시글 등록

	int checkMemberNickname(String memberNickname); // 닉네임 중복여부 검사

	int checkMemberEmail(String memberEmail); // 이메일 중복여부 검사

	MemberVO verifyMember(MemberVO memberVO); // 있는 회원인지 검사
	
}
