package com.mokcoding.ex05.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/example")
@Log4j
public class ExampleController {
		
	@GetMapping("/main")
	public void main() {
		log.info("main()");
	}
	
	@GetMapping("/member")
	public void member() {
		log.info("member()");
	}
	
	@GetMapping("/admin")
	public void admin() {
		log.info("admin()");
	}
	
	@GetMapping("/send")
	public void sendGET() {
		log.info("sendGET()");
	}
	
	@GetMapping("/output")
	public void output() {
		log.info("output()");
	}
	
	@PostMapping("/send")
	public String sendPOST(String title, String content, Model model) {
		log.info("sendPOST()");
		log.info("title = " + title);
		log.info("content = " + content);
		
		model.addAttribute("title", title);
		model.addAttribute("content", content);
		return "/example/output";
	}
	
}
