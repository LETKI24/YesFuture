package com.mokcoding.ex05.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mokcoding.ex05.service.CustomUserDetailsService;

// Spring Security의 설정을 정의하는 클래스
// WebSecurityConfigurerAdapter를 상속하여 보안 기능을 구성
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// 비밀번호 암호화를 위한 BCryptPasswordEncoder를 빈으로 생성
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// HttpSecurity 객체를 통해 HTTP 보안 기능을 구성
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.authorizeRequests()
				// "/example/main" URL에 대한 모든 사용자 접근을 허용
				.antMatchers("/example/main").permitAll()
				// "/example/admin" URL에 대한 ADMIN 사용자 접근을 허용
				.antMatchers("/example/admin").access("hasRole('ROLE_ADMIN')")
				// "/example/admin" URL에 대한 MEMBER 사용자 접근을 허용
				.antMatchers("/example/member").access("hasRole('ROLE_MEMBER')");

		// antMatchers(pattern) : 특정 url 패턴에 맞는 경로 매핑
		// permitAll() : 모든 사용자 접근
		// access() : 특정 권한을 가진 사용자 접근
		// hasRole('ROLE_XXX') : XXX 등급으로 권한 설정

		// 접근 제한 경로 설정
		httpSecurity.exceptionHandling().accessDeniedPage("/access/accessDenied"); // 거절 페이지 직접 설정

		httpSecurity.formLogin().loginPage("/access/login"); // 커스텀 로그인 url 설정

		httpSecurity.logout().logoutUrl("/access/logout") // logout url 설정
				.invalidateHttpSession(true); // 세션 무효화 설정

		// header 정보에 xssProtection 기능 설정
		httpSecurity.headers().xssProtection().block(true);
		httpSecurity.headers().contentSecurityPolicy("script-src 'self'"); // 스크립트 임의사용 막는 코드
	}

	// AuthenticationManagerBuilder 객체를 통해 인증 기능을 구성
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()); // CustomUserDetailsService 빈 적용
	}

	// 사용자 정의 로그인 클래스인 CustomUserDetailsService를 빈으로 생성
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

}