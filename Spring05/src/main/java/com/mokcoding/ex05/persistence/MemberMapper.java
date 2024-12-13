package com.mokcoding.ex05.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.mokcoding.ex05.domain.Member;
import com.mokcoding.ex05.domain.MemberRole;

@Mapper
public interface MemberMapper {
	int insertMember(Member member);
	int insertMemberRole(MemberRole memberRole);
	Member selectMemberByMemberId(String memberId);
	MemberRole selectRoleByMemberId(String memberId);
}
