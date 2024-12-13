package com.mokcoding.ex05.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mokcoding.ex05.config.RootConfig;
import com.mokcoding.ex05.config.SecurityConfig;
import com.mokcoding.ex05.domain.Member;
import com.mokcoding.ex05.domain.MemberRole;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) // 스프링 JUnit 실행 클래스 연결
@ContextConfiguration(classes = { RootConfig.class, SecurityConfig.class }) // 설정 파일 연결
@Log4j
public class MemberMapperTest {
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	public void test() {
		testInsertMember();
	}
	// 팁 : 임의로 테이블 만들고 테스트 한 다음에 그 때 이후에 security 해라 - 권한은 어려우니 나중에 수정해라
	
	private void testInsertMember() {
		Member member1 = new Member();
		member1.setMemberId("member1");
		member1.setMemberPw(passwordEncoder.encode("1234"));
		member1.setMemberName("홍길동");
		log.info(memberMapper.insertMember(member1) + "행 등록");
		
		MemberRole role1 = new MemberRole();
		role1.setMemberId(member1.getMemberId());
		role1.setRoleName("ROLE_MEMBER"); // ROLE_??? 의 형식으로 해야됨
		log.info(memberMapper.insertMemberRole(role1) + "행 등록");
		
		Member member2 = new Member();
		member2.setMemberId("admin1");
		member2.setMemberPw(passwordEncoder.encode("1234"));
		member2.setMemberName("고길동");
	
		log.info(memberMapper.insertMember(member2) + "행 등록");
		
		MemberRole role2 = new MemberRole();
		role2.setMemberId(member2.getMemberId());
		role2.setRoleName("ROLE_ADMIN");
		log.info(memberMapper.insertMemberRole(role2) + "행 등록");
	}
	
	
}
