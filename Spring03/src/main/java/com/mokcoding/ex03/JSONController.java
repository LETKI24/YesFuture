package com.mokcoding.ex03;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mokcoding.ex03.domain.ProductVO;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class JSONController {
	
	// 서버로 연결을 했는데 서버쪽이 프로젝트로 가끔 인식을 못하는 경우가 있음
	// 메시지도 '찾을 수가 없습니다' 가 아니고 '[/ex03]은(는) 가용하지 않습니다.' 라고 뜸
	// 해결 방법 : Restart 누르던 서버 우클릭 -> Add and Remove에서 Add 되어있는지 확인
	
	// jsp 페이지 전송
	@GetMapping("/jsp")
	public String returnJSP() {
		log.info("returnJSP()");
		return "home"; // home.jsp 호출
	}
	
	// JSON 문자열 전송
	@GetMapping("/json1")
	@ResponseBody // 리턴 결과가 바인딩된 JSON 데이터로 변경 
	// -> 이거 없으면 http://localhost:8080/ex03/json1로 가도 404 not found 뜨면서
	// -> '[/WEB-INF/views/Hello, Spring.jsp]을(를) 찾을 수 없습니다.' 라고 뜸 
	// 이거 추가하면 Hello, Spring을 표기하는 페이지로 이동함
	public String json1() {
		log.info("json1()");
		return "Hello, Spring";
	}
	
	// vo 객체를 JSON 데이터로 바인딩 후 전송
	@GetMapping("/json2")
	@ResponseBody // vo 객체를 JSON 데이터로 바인딩하게 해주는 코드
	public ProductVO json2() {
		log.info("json2() 호출");
		ProductVO product = new ProductVO();
		product.setName("야구공");
		product.setPrice(10000);
		return product;
	}
	
	// List 객체를 JSON 데이터로 바인딩 후 전송
	// From Server to Client
	@GetMapping("/json3") 
	@ResponseBody
	public List<ProductVO> json3() {
		log.info("json3() 호출");
		ProductVO product1 = new ProductVO();
		product1.setName("야구공");
		product1.setPrice(10000);
		
		ProductVO product2 = new ProductVO();
		product2.setName("축구공");
		product2.setPrice(15000);
		
		List<ProductVO> list = new ArrayList<ProductVO>();
		list.add(product1);
		list.add(product2);
		
		return list;
	}
	
	// JSON 데이터를 전송받아 자바 객체로 변환
	// From Client to Server
	@PostMapping("/json4") // Post 방식이라 url 바꿔서(Get방식) 잘 되는지 테스트할 수는 없음, POSTMAN ㄱㄱ
	@ResponseBody
	public String json4(@RequestBody ProductVO vo) { // 이름을 잘 매칭해둬야 '바인딩'이 잘 된다.
		// @RequestBody : 지정된 객체에 JSON 데이터 바인딩
		log.info(vo);
		return "success";
	}
	
}

