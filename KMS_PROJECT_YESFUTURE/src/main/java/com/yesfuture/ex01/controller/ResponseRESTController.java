package com.yesfuture.ex01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yesfuture.ex01.domain.CustomUser;
import com.yesfuture.ex01.domain.TrainingResponseVO;
import com.yesfuture.ex01.service.ResponseService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/response") // url : /ex01/response
@Log4j
public class ResponseRESTController {

	@Autowired
	private ResponseService responseService;
	
	
	@PutMapping("/saveResponse")
	public ResponseEntity<Integer> updateResponse(@AuthenticationPrincipal CustomUser customUser, 
												  @RequestBody TrainingResponseVO trainingResponseVO) {
		log.info("updateResponse 도착");
		
		trainingResponseVO.setMemberId(customUser.getMember().getMemberId());
		log.info("trainingResponseVO : " + trainingResponseVO);
		
		int result = responseService.updateResponse(trainingResponseVO);
		
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	
}
