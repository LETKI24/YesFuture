package com.yesfuture.ex01.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yesfuture.ex01.domain.BoardQuestionVO;
import com.yesfuture.ex01.domain.MemberVO;
import com.yesfuture.ex01.service.MemberService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value = "/board") // url : /ex01/board
@Log4j
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/main")
	public void main() {
		log.info("main()");
	}

	// agree.jsp 페이지 호출
	@GetMapping("/agree")
	public void agreeGET() {
		log.info("agreeGET()");
	}

	// join.jsp 페이지 호출
	@GetMapping("/join")
	public void joinGET() {
		log.info("joinGET()");
	}

	// join.jsp에서 전송받은 회원 데이터를 저장
	@PostMapping("/join")
	public String joinPOST(MemberVO memberVO) {
		log.info("joinPOST()");
		log.info("memberVO = " + memberVO.toString());
		// 비밀번호 암호화
		String encPw = passwordEncoder.encode(memberVO.getMemberPw());
		memberVO.setMemberPw(encPw); // 암호화된 데이터 적용
		int result = memberService.createMember(memberVO);
		log.info(result + "행 등록");
		return "redirect:/auth/login";
	}

	@PostMapping("/nickname")
	@ResponseBody
	public ResponseEntity<String> checkMemberNickname(@RequestParam("memberNickname") String memberNickname) {
		log.info("checkMemberNickname");
		System.out.println("memberNickname : " + memberNickname);

		int result = memberService.checkMemberNickname(memberNickname);

		return new ResponseEntity<>(String.valueOf(result), HttpStatus.OK);
	}

	@PostMapping("/email")
	@ResponseBody
	public ResponseEntity<String> checkMemberEmail(@RequestParam("memberEmail") String memberEmail) {
		log.info("checkMemberEmail");
		System.out.println("memberEmail : " + memberEmail);

		int result = memberService.checkMemberEmail(memberEmail);

		return new ResponseEntity<>(String.valueOf(result), HttpStatus.OK);
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		log.info("logout() 호출");

		// 세션 확인 후 세션 제거
		HttpSession session = request.getSession(false); // 기존 세션이 없으면 null 반환
		if (session != null && session.getAttribute("memberEmail") != null) {
			session.invalidate(); // 세션 무효화 (선택사항)
			log.info("로그아웃 성공");
		}
		// 로그아웃 후 이동할 페이지 (index.jsp 또는 다른 페이지)
		return "redirect:/board/main";
	}
	
	@GetMapping("/info")
	public void info() {
		log.info("info()");
	}
	
	
} // end MapperController
