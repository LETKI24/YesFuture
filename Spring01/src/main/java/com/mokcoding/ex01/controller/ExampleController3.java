package com.mokcoding.ex01.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mokcoding.ex01.domain.InfoVO;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/example3")
@Log4j // Lombok log4j 기능
public class ExampleController3 {
		
	// info.jsp 페이지 호출
	@GetMapping("/info") // jsp를 불러온다는 뜻, example3/info.jsp로 ㄱㄱ
	public void infoGET(Model model) { // Model 객체 사용
		// Model : Map 형식으로 데이터를 전송하는 객체
		log.info("infoGET()");
		InfoVO vo = new InfoVO();
		vo.setName("김진혁");
		vo.setEmail("test@test.com");
		vo.setBirthDate(new Date());
		
		model.addAttribute("vo", vo); // model에 name="vo", object=vo 저장
	} // end infoGET()
	
	// page3.jsp 페이지 호출 및 데이터 전송
	@GetMapping("/page3")
	public void page3(@ModelAttribute("name") String name, 
			@ModelAttribute("age") int age) {
		// @ModelAttribute() : request parameter로 전달받은 데이터를
		// Model 객체에 담아서 view로 전달해주는 역할 (받은거 고대로 던져준다)
		log.info("page3()");
	}
	
	// result() 메소드로 이동(redirect)
	@GetMapping("/redirect")
	public String redirect(RedirectAttributes reAttr) {
		log.info("redirect()");
		// RedirectAttributes : 일회성 데이터 전달 객체. attribute 방식
		reAttr.addFlashAttribute("result", "success");
		// FlashAttribute : 세션에 저장되어 사용 후 자동 삭제.
		// url 파라미터에 표시되지 않음
		return "redirect:/example3/result";
	}
	
	// result.jsp 페이지 호출
	@GetMapping("/result")
	public void result() {
		log.info("result()");
	}
	
} // end ExampleController2