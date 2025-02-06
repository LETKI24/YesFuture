package com.yesfuture.ex01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yesfuture.ex01.domain.AttachProblem;
import com.yesfuture.ex01.domain.AttachProblemVO;
import com.yesfuture.ex01.domain.AttachQuestion;
import com.yesfuture.ex01.domain.AttachQuestionVO;
import com.yesfuture.ex01.persistence.AttachProblemMapper;
import com.yesfuture.ex01.persistence.AttachQuestionMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class AttachServiceImple implements AttachService {
	
	@Autowired
    private AttachQuestionMapper attachQuestionMapper;
	
	@Override
	public AttachQuestionVO getAttachQuestionById(int attachQuestionId) {
		log.info("getAttachById()");
        return toVO(attachQuestionMapper.selectByAttachQuestionId(attachQuestionId));
	}
	
	// Attach를 AttachDTO로 변환하는 메서드
    private AttachQuestionVO toVO(AttachQuestion attachQuestion) {
        AttachQuestionVO attachQuestionVO = new AttachQuestionVO();
        attachQuestionVO.setAttachQuestionId(attachQuestion.getAttachQuestionId());
        attachQuestionVO.setBoardQuestionId(attachQuestion.getBoardQuestionId());
        attachQuestionVO.setAttachQuestionPath(attachQuestion.getAttachQuestionPath());
        attachQuestionVO.setAttachQuestionRealName(attachQuestion.getAttachQuestionRealName());
        attachQuestionVO.setAttachQuestionChgName(attachQuestion.getAttachQuestionChgName());
        attachQuestionVO.setAttachQuestionExtension(attachQuestion.getAttachQuestionExtension());
        attachQuestionVO.setAttachQuestionDateCreated(attachQuestion.getAttachQuestionDateCreated());

        return attachQuestionVO;
    }
    
	@Autowired
    private AttachProblemMapper attachProblemMapper;

	@Override
	public AttachProblemVO getAttachProblemById(int attachProblemId) {
		log.info("getAttachById()");
        return attachProblemMapper.selectByAttachProblemId(attachProblemId);
	}
//	
//	// Attach를 AttachDTO로 변환하는 메서드
//	private AttachProblemVO toVO(AttachProblem attachProblem) {
//	    AttachProblemVO attachProblemVO = new AttachProblemVO();
//	    attachProblemVO.setAttachProblemId(attachProblem.getAttachProblemId());
//	    attachProblemVO.setProblemId(attachProblem.getProblemId());
//	    attachProblemVO.setAttachProblemPath(attachProblem.getAttachProblemPath());
//	    attachProblemVO.setAttachProblemRealName(attachProblem.getAttachProblemRealName());
//	    attachProblemVO.setAttachProblemChgName(attachProblem.getAttachProblemChgName());
//	    attachProblemVO.setAttachProblemExtension(attachProblem.getAttachProblemExtension());
//	    attachProblemVO.setAttachProblemDateCreated(attachProblem.getAttachProblemDateCreated());
//
//	    return attachProblemVO;
//	}

}
