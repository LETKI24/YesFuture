package com.mokcoding.ex03;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mokcoding.ex03.domain.ProductVO;

import lombok.extern.log4j.Log4j;

@RestController // @Controller와 @ResponseBody를 결합한 역할, 이걸 쓰면 다 JSON 리턴타입으로 알아서 바꿔줌
// 단점 : 웹 페이지 렌더링 불가(=JSP 못쓴다?) = 이 기능은 주로 데이터를 반환하는데 사용되고 HTML을 반환하지 못한다.
// 결론 : 댓글 기능 때문에 쓰는거다.
@RequestMapping("/product") // API가 제공하는 리소스를 경로 이름으로 설정
@Log4j
public class ProductRESTController {

	// 리소스 읽기(GET)
	// ㄴ 제품 name에 맞는 price를 읽기
	@GetMapping("{name}") // url로 name 값을 전송. parameter(?해서 추가했던방법)와는 다름 
	// -> 장점 : parameter형태를 사용자가 몰라서 약간의 보안기능
	public ResponseEntity<Integer> readProduct(@PathVariable("name") String name) {
		// !!!!! @PathVariable의 괄호 안에 들어가는 것은 @GetMapping의 괄호 안에 들어가는 것과 같아야한다. !!!!!
		// @PathVariable : url로 전송된 데이터를 매개변수에 적용
		log.info("readProduct()");
		log.info("name = " + name);

		int price = 20000;
		// ResponseEntity : HTTP 응답 상태 코드. 헤더 및 본문(body)을 포함하여 전송
		return new ResponseEntity<Integer>(price, HttpStatus.OK);
	}

	// 리소스 생성(POST)
	// ㄴ 제품 데이터를 생성
	@PostMapping
	public ResponseEntity<Integer> createProduct(@RequestBody ProductVO vo) {
		log.info("createProduct()");
		log.info(vo);

		int result = 1;
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

}
