package com.yesfuture.ex01.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yesfuture.ex01.domain.AttachProblem;
import com.yesfuture.ex01.domain.AttachProblemVO;
import com.yesfuture.ex01.domain.Problem;
import com.yesfuture.ex01.domain.ProblemOption;
import com.yesfuture.ex01.domain.ProblemVO;
import com.yesfuture.ex01.persistence.AttachProblemMapper;
import com.yesfuture.ex01.persistence.ProblemMapper;
import com.yesfuture.ex01.persistence.ProblemOptionMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ProblemServiceImple implements ProblemService{
	
	@Autowired
	private ProblemMapper problemMapper;
	
	@Autowired
	private ProblemOptionMapper problemOptionMapper;
	
	@Autowired
	private AttachProblemMapper attachProblemMapper;
	
	@Transactional(value = "transactionManager") 
	@Override
	public int createProblem(ProblemVO problemVO, String optionContentAll) {
		log.info("createProblem()");
		log.info("problemVO : " + problemVO);
		log.info("optionContentAll : " + optionContentAll);
		
		int insertProblemResult = problemMapper.insert(toEntity(problemVO));
		log.info(insertProblemResult + "행 게시글 등록");
		
		int insertProblemOptionResult = problemOptionMapper.insert(optionContentAll); 
		log.info(insertProblemOptionResult + "행 게시글 등록");
		
		List<AttachProblemVO> attachProblemList = problemVO.getAttachProblemList();

		int insertAttachProblemResult = 0;
		for(AttachProblemVO attachProblemVO : attachProblemList) {
			insertAttachProblemResult += attachProblemMapper.insert(attachProblemVO);
		}
		log.info(insertAttachProblemResult + "행 파일 정보 등록");
		
		return 1;
	}
	
	@Override
	public List<ProblemVO> getProblemByPart(String[] partArray, String[] yearArrayStr) {
        
		
		int[] yearArray = Arrays.stream(yearArrayStr)
                				.mapToInt(Integer::parseInt)
                				.toArray();
		// problemPart와 problemYear로 List<Problem>을 가져오기
        List<Problem> problems = problemMapper.selectProblemByPartAndYear(partArray, yearArray);
        
        log.info("problems : " + problems);
        
        // 문제가 없는 경우 빈 리스트 반환
        if (problems == null || problems.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 조회된 Problem 리스트에서 problemId만 추출
        List<Integer> problemIdList = problems.stream()
                                              .map(Problem::getProblemId)
                                              .collect(Collectors.toList());
        
        // 추출한 problemId 목록을 이용해서 한 번에 해당하는 ProblemOption들과 첨부이미지를 조회
        List<ProblemOption> options = problemOptionMapper.selectByProblemId(problemIdList);
        log.info("options : " + options);
        
        List<AttachProblemVO> images = attachProblemMapper.selectByProblemId(problemIdList);
        log.info("images : " + images);
        
        
        // List<Problem> problems와 List<ProblemOption> options를 합하여 List<ProblemVO> problemList 만들기
        // 문제와 해당 옵션을 합쳐 ProblemVO로 변환
        List<ProblemVO> problemVOList = new ArrayList<>();
        
        for (Problem problem : problems) {

        }
                
		return problemVOList ;
	}
		
	
	// ProblemVO 데이터를 Problem에 적용하는 메서드
	public Problem toEntity(ProblemVO problemVO) {
		Problem problem = new Problem();
		problem.setProblemId(problemVO.getProblemId());
		problem.setProblemContent(problemVO.getProblemContent());
		problem.setProblemDiagnosis(problemVO.getProblemDiagnosis());
		problem.setProblemYear(problemVO.getProblemYear());
		problem.setProblemSource(problemVO.getProblemSource());
		problem.setProblemPart(problemVO.getProblemPart());
		problem.setMemberId(problemVO.getMemberId());
		return problem;
	} // end toEntity()
	
	// Problem 데이터를 ProblemVO에 적용하는 메서드
	public ProblemVO toVO(Problem problem) {
		ProblemVO problemVO = new ProblemVO();
		problemVO.setProblemId(problem.getProblemId());
		problemVO.setProblemContent(problem.getProblemContent());
		problemVO.setProblemDiagnosis(problem.getProblemDiagnosis());
		problemVO.setProblemYear(problem.getProblemYear());
		problemVO.setProblemSource(problem.getProblemSource());
		problemVO.setProblemPart(problem.getProblemPart());
		problemVO.setMemberId(problem.getMemberId());
		return problemVO;
	} // end toVO()
	
	// AttachProblemVO를 AttachProblem로 변환하는 메서드
	private AttachProblem toEntity(AttachProblemVO attachProblemVO) {
	    AttachProblem attachProblem = new AttachProblem();
	    attachProblem.setAttachProblemId(attachProblemVO.getAttachProblemId());
	    attachProblem.setProblemId(attachProblemVO.getProblemId());
	    attachProblem.setAttachProblemPath(attachProblemVO.getAttachProblemPath());
	    attachProblem.setAttachProblemRealName(attachProblemVO.getAttachProblemRealName());
	    attachProblem.setAttachProblemChgName(attachProblemVO.getAttachProblemChgName());
	    attachProblem.setAttachProblemExtension(attachProblemVO.getAttachProblemExtension());
	    attachProblem.setAttachProblemDateCreated(attachProblemVO.getAttachProblemDateCreated());
	    return attachProblem;
	}

	// AttachProblem를 AttachProblemVO로 변환하는 메서드
	private AttachProblemVO toVO(AttachProblem attachProblem) {
	    AttachProblemVO attachProblemVO = new AttachProblemVO();
	    attachProblemVO.setAttachProblemId(attachProblem.getAttachProblemId());
	    attachProblemVO.setProblemId(attachProblem.getProblemId());
	    attachProblemVO.setAttachProblemPath(attachProblem.getAttachProblemPath());
	    attachProblemVO.setAttachProblemRealName(attachProblem.getAttachProblemRealName());
	    attachProblemVO.setAttachProblemChgName(attachProblem.getAttachProblemChgName());
	    attachProblemVO.setAttachProblemExtension(attachProblem.getAttachProblemExtension());
	    attachProblemVO.setAttachProblemDateCreated(attachProblem.getAttachProblemDateCreated());
	    return attachProblemVO;
	}
	
}
