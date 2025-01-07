package com.yesfuture.ex01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yesfuture.ex01.domain.Member;
import com.yesfuture.ex01.domain.MemberVO;
import com.yesfuture.ex01.persistence.MemberMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MemberServiceImple implements MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Transactional(value = "transactionManager")
	@Override
	public int createMember(MemberVO memberVO) {
		log.info("createMember()");
		int result = memberMapper.insert(toEntity(memberVO));
		log.info(result + "행 회원 정보 등록");;
		
		int insertMemberRole = memberMapper.insertMemberRole(memberVO.getMemberEmail());
		log.info(insertMemberRole + "행 권한 정보 등록");
		
		return 1;
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
	
	
	// member 정보 조회할 때 쓸 것
//    public MemberVO toVO(Member member) {
//    	MemberVO memberVO = new MemberVO();
//        memberVO.setMemberId(member.getMemberId());
//        memberVO.setMemberPw(member.getMemberPw());
//        memberVO.setMemberName(member.getMemberName());
//        memberVO.setRegDate(member.getRegDate());
//        memberVO.setEnabled(member.getEnabled());
//        return memberVO;
//    }

    public Member toEntity(MemberVO memberVO) {
        Member entity = new Member();
        entity.setMemberId(memberVO.getMemberId());
        entity.setMemberName(memberVO.getMemberName());
        entity.setMemberNickname(memberVO.getMemberNickname());
        entity.setMemberEmail(memberVO.getMemberEmail());
        entity.setMemberPw(memberVO.getMemberPw());
        entity.setMemberSchool(memberVO.getMemberSchool());
        entity.setMemberPhone(memberVO.getMemberPhone());
        entity.setMemberDateCreated(memberVO.getMemberDateCreated());
        entity.setMemberSelected(memberVO.getMemberSelected());
        entity.setMemberEnabled(memberVO.getMemberEnabled());
        return entity;
    }

}
