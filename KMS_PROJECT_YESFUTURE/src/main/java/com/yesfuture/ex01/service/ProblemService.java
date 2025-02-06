package com.yesfuture.ex01.service;

import java.util.List;

import com.yesfuture.ex01.domain.Problem;
import com.yesfuture.ex01.domain.ProblemVO;

public interface ProblemService {
	int createProblem(ProblemVO problemVO, String optionContentAll);

	List<Problem> getProblemByPart(String[] partArray, String[] yearArray);
	
}
