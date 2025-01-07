package com.mokcoding.mysite.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.mokcoding.mysite.domain.Member;
import com.mokcoding.mysite.domain.MemberRole;

@Mapper
public interface MemberMapper {
	int insertMember(Member member);
	int insertMemberRole(String memberId);
	Member selectMemberByMemberId(String memberId);
	MemberRole selectRoleByMemberId(String memberId);
	int selectExistingMemberId(String memberId);
	int updateMember(Member member);
	int deleteMember(String memberId);
	int deleteMemberRole(String memberId);
}
