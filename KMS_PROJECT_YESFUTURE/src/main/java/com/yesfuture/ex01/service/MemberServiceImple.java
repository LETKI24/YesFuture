package com.yesfuture.ex01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yesfuture.ex01.domain.MemberVO;
import com.yesfuture.ex01.persistence.MemberMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MemberServiceImple implements MemberService {

	@Autowired
	private MemberMapper memberMapper;

	@Override
	public int createMember(MemberVO memberVO) {
		log.info("createMember()");
		int result = memberMapper.insert(memberVO);
		System.out.println("result : " + result);
		return result;
	}

	@Override
	public int checkMemberNickname(String memberNickname) {
		log.info("checkMemberNickname()");
		int result = memberMapper.checkMemberNickname(memberNickname);
		System.out.println("result : " + result);
		return result;
	}

	@Override
	public int checkMemberEmail(String memberEmail) {
		log.info("checkMemberEmail()");
		int result = memberMapper.checkMemberEmail(memberEmail);
		System.out.println("result : " + result);
		return result;
	}

	@Override
	public MemberVO verifyMember(MemberVO memberVO) {
		// 데이터베이스에서 이메일과 비밀번호가 일치하는 회원 확인
		return memberMapper.verifyMember(memberVO); // memberVO 객체 반환
	}

}
