package com.yesfuture.ex01.service;

import com.yesfuture.ex01.domain.AttachProblemVO;
import com.yesfuture.ex01.domain.AttachQuestionVO;

public interface AttachService {
	
	AttachQuestionVO getAttachQuestionById(int attachQuestionId);

	AttachProblemVO getAttachProblemById(int attachProblemId);

}
