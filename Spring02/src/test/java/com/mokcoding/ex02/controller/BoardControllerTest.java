package com.mokcoding.ex02.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mokcoding.ex02.config.RootConfig;
import com.mokcoding.ex02.config.ServletConfig;

import lombok.extern.log4j.Log4j;
import oracle.jdbc.OracleDriver;

@RunWith(SpringJUnit4ClassRunner.class) // 스프링 JUnit test 연결
@ContextConfiguration(classes= {ServletConfig.class}) // 설정 파일 연결 - Test하고 싶은 '걔'를 데려온다
@WebAppConfiguration
@Log4j
public class BoardControllerTest {
	
	@Autowired
	private WebApplicationContext wac; // 웹 애플리케이션 객체 , 챕터 4 스프링 MVC 프로젝트 전체 구조 그림 참조
	
	// 스프링 MVC를 테스트하는 mock-up(테스트용 모조품) 객체
	private MockMvc mock;
	
	@Before
	// JUnit 테스트를 실행하기 전에 초기화 작업을 수행하는 메소드
	public void beforeTest() {
		log.info("beforeTest()");
		mock = MockMvcBuilders.webAppContextSetup(wac).build();
		
	}
	
	@Test
	public void test() { // list와 register 2개만 테스트 해보자
//		testList();
		testRegister();
	}

	private void testRegister() {
		log.info("testRegister()");
		
		RequestBuilder requestBuilder = post("/board/register") // post 방식, parameter로 보내자
				.param("boardTitle", "게시글 등록2")
				.param("boardContent", "게시글 테스트2")
				.param("memberId", "admin"); 
		// request parameter 데이터 적용
		try {
			log.info(mock.perform(requestBuilder).andReturn());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void testList() { // boardController의 list를 테스트, GET방식으로 그쪽 데이터 가져오기
		log.info("testList()");
		
		RequestBuilder requestBuilder = get("/board/list");
		
		// get(String urlTemplate) : GET 요청에 대한 RequestBuilder 생성.
		// post(String urlTemplate) : POST 요청에 대한 RequestBuilder 생성.
		
		try {
			log.info(mock.perform(requestBuilder) // mock 객체 실행
					.andReturn() // request 실행 결과 리턴
					.getModelAndView()); // ModelAndView 객체 리턴 
			// - view는 경로, model은 pagination도 있고 pageMaker도 있고, boardList도 있다 
			// - boardController.java의 list에 그렇게 넣었으니까
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
