package com.mokcoding.ex01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mokcoding.ex01.domain.InfoVO;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/example2")
@Log4j // Lombok log4j 기능
public class ExampleController2 {
	
	// 페이지 호출 역할(GET)
	@GetMapping("/send")
	public void sendGET() {
		log.info("sendGET()"); // log 출력 기능 // log를 따로 선언도 안했는데 사용가능, @Log4j에 기능이 있어서 그럼
	}
	
	// 데이터 수신 역할(POST)
	@PostMapping("/send") // RequestMapping POST
	// 데이터 꺼내려면 request.getParameter 썼는데 스프링에서는 다음과 같다
	public String sendPOST(@RequestParam("name") String name,
			@RequestParam("age") int age) { // 심지어 여기서도 더 줄일 수 있고 
		// 알아서 형변환도 해준다(getParameter는 디폴트가 String type임)
		
		// String name = request.getParameter("name");
		// int age = Integer.parseInt(request.getParameter("age"));
		
		log.info("sendPOST()");
		log.info("name = " + name + ", age = " + age);
		
		return "redirect:/"; // response.sendRedirect(); 와 같은 기능을 구현한다. 코드 파악이 어려운 스프링 
	}
	
	// info.jsp 페이지 호출
	@GetMapping("/info")
	public void infoGET() {
		log.info("infoGET()");
	}
	
	// info.jsp 페이지에서 전송된 데이터 수신
	@PostMapping("/info")
	public String infoPOST(InfoVO vo) {
		// 전송된 데이터는 InfoVO에 바인딩(Binding)
		log.info("infoPOST()");
		log.info("vo = " + vo.toString());
		
		return "redirect:/";
	}
} // end ExampleController2