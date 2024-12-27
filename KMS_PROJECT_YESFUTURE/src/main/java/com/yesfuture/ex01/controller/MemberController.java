package com.yesfuture.ex01.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		int result = memberService.createMember(memberVO);
		log.info(result + "행 등록");
		return "redirect:/board/main";
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

	// login.jsp 페이지 호출
	@GetMapping("/login")
	public void loginGET() {
		log.info("loginGET()");
	}

	// login.jsp에서 전송받은 회원 데이터를 조회
	@PostMapping("/login")
	public String loginPOST(MemberVO memberVO, HttpSession session, Model model) {
		log.info("loginPOST() 호출");

		// 이메일과 비밀번호 검증
		MemberVO verifyMember = memberService.verifyMember(memberVO);

		if (verifyMember != null) { // 로그인 성공
			log.info("로그인 성공: " + memberVO.getMemberEmail());
			session.setAttribute("memberEmail", verifyMember.getMemberEmail()); // 세션에 이메일 저장
			session.setAttribute("memberNickname", verifyMember.getMemberNickname()); // 세션에 닉네임 저장
			session.setAttribute("memberId", verifyMember.getMemberId()); // 세션에 Id 저장
			
			log.info("memberEmail :" + session.getAttribute("memberEmail"));
			log.info("memberNickname :" + session.getAttribute("memberNickname"));
			
			session.setMaxInactiveInterval(3600); // session 60분 설정
			return "redirect:/board/main"; // 메인 페이지로 이동
		} else { // 로그인 실패
			log.info("로그인 실패: 이메일 또는 비밀번호 불일치");
			model.addAttribute("loginError", "이메일 또는 비밀번호를 확인하세요.");
			return "board/login"; // 로그인 페이지로 다시 이동
		}
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
	
	
	
} // end MapperController
