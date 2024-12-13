package com.mokcoding.ex05.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mokcoding.ex05.domain.CustomUser;
import com.mokcoding.ex05.domain.Member;
import com.mokcoding.ex05.domain.MemberRole;
import com.mokcoding.ex05.persistence.MemberMapper;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService implements UserDetailsService { 
	
    @Autowired
    private MemberMapper memberMapper;
   
    // 전송된 username으로 사용자 정보를 조회하고, UserDetails에 저장하여 리턴하는 메서드 
    // 사용자가 로그인할 때 전송, 데이터가 있으면 UserDetails에 묶어서 보내준다?
    // security가 사용자 정보를 관리하는거다
    @Override
    public UserDetails loadUserByUsername(String username) {
    	log.info("loadUserByUsername()");
    	log.info(username);
        // 사용자 ID를 이용하여 회원 정보와 권한 정보를 조회
        Member member = memberMapper.selectMemberByMemberId(username);
        MemberRole role = memberMapper.selectRoleByMemberId(username);
        
        // 조회된 회원 정보가 없을 경우 예외 처리
        if (member == null) {
            throw new UsernameNotFoundException("UsernameNotFound");
        }
        
        // 회원의 역할을 Spring Security의 GrantedAuthority 타입으로 변환하여 리스트에 추가
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        // 권한 정보는 리스트 타입, 권한 종류가 여러개라서
        
        // UserDetails 객체를 생성하여 회원 정보와 역할 정보를 담아 반환
        // userDetails에서 admin.jsp의 principal로 간다
        UserDetails userDetails = new CustomUser(member, 
								        		authorities);
        // 다른데서 principal로 불린다. -> src/main/WEB-INF 하위(example)에 admin.jsp로 ㄱㄱ
        
        return userDetails;
    }

}
