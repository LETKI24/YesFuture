package com.yesfuture.ex01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value = "/problem") // url : /ex01/problem
@Log4j
public class ProblemController {

	// problemMain.jsp 페이지 호출
	@GetMapping("/problemMain")
	public void boardMain() {
		log.info("problemMain()");
	}
	
}
