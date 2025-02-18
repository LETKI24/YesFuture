package com.yesfuture.ex01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yesfuture.ex01.domain.TrainingResponseVO;
import com.yesfuture.ex01.persistence.TrainingResponseMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ResponseServiceImple implements ResponseService{

	@Autowired
	private TrainingResponseMapper trainingResponseMapper;
	
	@Override
	public int updateResponse(TrainingResponseVO trainingResponseVO) {
		
		return trainingResponseMapper.update(trainingResponseVO);
	}

}
