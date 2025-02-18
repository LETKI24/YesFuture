package com.yesfuture.ex01.service;

import java.util.List;

import com.yesfuture.ex01.domain.Problem;
import com.yesfuture.ex01.domain.ProblemVO;

public interface ProblemService {
	int createProblem(ProblemVO problemVO, String[] optionArray);

	List<Integer> getProblemCount(String[] partArray, String[] yearArray);

	List<ProblemVO> getProblemByIds(List<Integer> problemIds);

	boolean getHistoryBoolean(int memberId);

	int createOMRcard(int memberId, List<Integer> problemIds);

	int createHistory(int memberId, String[] partArray);
	
}
