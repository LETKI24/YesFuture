package com.mokcoding.ex01.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// POJO(Plain Old Java Object)
// 스프링은 상속을 잘 안쓰고 뱃지를 준다. 뱃지 = annotation

@Controller // @Component로 가능
// @Controller로 선언된 객체는 Spring Container에서 관리
@RequestMapping("/example1") // 상위 경로
public class ExampleController1 {
	// 로그를 사용하기 위한 객체
	private static final Logger logger = 
			LoggerFactory.getLogger(ExampleController1.class); // 클래스 및 메소드의 위치정보를 로그로 알려줌
	
	// @RequestMapping : 특정 메소드를 요청에 대한 처리가 가능하도록 만드는
	// 어노테이션, URL, HTTP Method 등의 속성을 갖고 있음
	@RequestMapping(value="/page1", method=RequestMethod.GET) 
	// @GetMapping("/page1") = @RequestMapping(value="/page1", method=RequestMethod.GET) 의 약어 
	// /example1 하위에 /page1로 GET방식의 요청이 오면 여기로 온다.
	public String page1() {
		logger.info("page1()");
		return "example1/page1"; //샘 주석 : jsp 경로 및 파일 이름(view name)
		// WEB-INF/views/example1/page1.jsp로 찾아감
		// (servelet-context.xml에 설정되어 있음)
	}
	// 이대로 저장하고 실행하고 http://localhost:8080/ex01/example1/page1 이 URL로 가보면
	// [/WEB-INF/views/example1/page1.jsp]을(를) 찾을 수 없습니다. 면서 404 뜸, -> Spring이 알아서 저기로 어레인지 해준 것
	// WEB-INF/spring/appServlet의 servelet-context.xml에서 머리에 /WEB-INF/views/랑 꼬리에 .jsp를 자동으로 붙여주는 코드가 있음
	
	
	@GetMapping("/page2") // short cut
	public void page2() {
		logger.info("page2()");
		// return 타입이 void인 경우는 URL 매핑을 통해서 view를 찾음
	} // end page2()

} // end ExampleController1
