package com.yesfuture.ex01.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yesfuture.ex01.domain.AttachProblem;
import com.yesfuture.ex01.domain.AttachProblemVO;
import com.yesfuture.ex01.domain.CorrectOption;
import com.yesfuture.ex01.domain.Problem;
import com.yesfuture.ex01.domain.ProblemOption;
import com.yesfuture.ex01.domain.ProblemVO;
import com.yesfuture.ex01.domain.TrainingRecordVO;
import com.yesfuture.ex01.domain.TrainingResponseVO;
import com.yesfuture.ex01.persistence.AttachProblemMapper;
import com.yesfuture.ex01.persistence.CorrectOptionMapper;
import com.yesfuture.ex01.persistence.ProblemMapper;
import com.yesfuture.ex01.persistence.ProblemOptionMapper;
import com.yesfuture.ex01.persistence.TrainingHistoryMapper;
import com.yesfuture.ex01.persistence.TrainingRecordMapper;
import com.yesfuture.ex01.persistence.TrainingResponseMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ProblemServiceImple implements ProblemService{
	
	@Autowired
	private ProblemMapper problemMapper;
	
	@Autowired
	private ProblemOptionMapper problemOptionMapper;
	
	@Autowired
	private CorrectOptionMapper correctOptionMapper;
	
	@Autowired
	private AttachProblemMapper attachProblemMapper;
	
	@Autowired
	private TrainingResponseMapper trainingResponseMapper;
	
	@Autowired
	private TrainingHistoryMapper trainingHistoryMapper;
	
	@Autowired
	private TrainingRecordMapper trainingRecordMapper; 
	
	@Transactional(value = "transactionManager") 
	@Override
	public int createProblem(ProblemVO problemVO, String[] optionArray) {
		log.info("createProblem()");
		log.info("problemVO : " + problemVO);
		log.info("optionArray : " + Arrays.toString(optionArray));
		
		// 문제 본문 등록
		int insertProblemResult = problemMapper.insert(toEntity(problemVO));
		log.info(insertProblemResult + "행 게시글 등록");
		
		// 문제 객관식 보기 등록
		int insertProblemOptionResult = 0;
		for(String option : optionArray) {
			insertProblemOptionResult += problemOptionMapper.insert(option);
		}
		log.info(insertProblemOptionResult + "행 객관식 보기 등록");
		
		// 문제 정답 등록
		int insertCorrectOptionResult = correctOptionMapper.insert(optionArray[0]);
		log.info(insertCorrectOptionResult + "행 정답 등록");
		
		// 문제 첨부파일 등록
		List<AttachProblemVO> attachProblemList = problemVO.getAttachProblemList();

		int insertAttachProblemResult = 0;
		for(AttachProblemVO attachProblemVO : attachProblemList) {
			insertAttachProblemResult += attachProblemMapper.insert(attachProblemVO);
		}
		log.info(insertAttachProblemResult + "행 파일 정보 등록");
		
		return 1;
	}
	
	@Override
	public List<Integer> getProblemCount(String[] partArray, String[] yearArrayStr) {
		
		int[] yearArray = Arrays.stream(yearArrayStr)
				.mapToInt(Integer::parseInt)
				.toArray();
		
		List<Integer> problemIdList = problemMapper.selectProblemCount(partArray, yearArray);
		
		log.info("problemIdList : " + problemIdList);
		
        // 문제가 없는 경우 빈 리스트 반환
        if (problemIdList == null || problemIdList.isEmpty()) {
            return new ArrayList<>();
        }
		
		return problemIdList;
	}
	
	@Override
	public List<ProblemVO> getProblemByIds(List<Integer> problemIds) {
		List<ProblemVO> problemVOList = new ArrayList<>();

		// problemPart와 problemYear로 List<Problem>을 가져오기
        List<Problem> problems = problemMapper.selectProblemByIds(problemIds);
        
        log.info("problems : " + problems);
        
        // 문제가 없는 경우 빈 리스트 반환
        if (problems == null || problems.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 추출한 problemId 목록을 이용해서 한 번에 해당하는 ProblemOption들과 첨부이미지를 조회
        List<ProblemOption> options = problemOptionMapper.selectByProblemId(problemIds);
        log.info("options : " + options);
        
        List<AttachProblemVO> images = attachProblemMapper.selectByProblemId(problemIds);
        log.info("images : " + images);
        
        
        // List<Problem> problems와 List<ProblemOption> options를 합하여 List<ProblemVO> problemList 만들기
        // 문제와 해당 옵션을 합쳐 ProblemVO로 변환
        
        for (Problem problem : problems) {
        	List<ProblemOption> optionList = new ArrayList<>();
        	List<AttachProblemVO> attachList = new ArrayList<>();
        	
            for (ProblemOption option : options) {
                if (problem.getProblemId() == option.getProblemId()) {
                    // 문제와 옵션의 problemId가 일치하면 optionContent 설정
                	optionList.add(option);
                }
            }
            
            for (AttachProblemVO image : images) {
                if (problem.getProblemId() == image.getProblemId()) {
                    // 문제와 옵션의 problemId가 일치하면 optionContent 설정
                	attachList.add(image);
                }
            }
            
            ProblemVO vo = toVO(problem);
            
            vo.setOptionList(optionList);
            vo.setAttachProblemList(attachList);
            
            // 생성한 ProblemVO 객체를 리스트에 추가
            problemVOList.add(vo);

        }
        
        return problemVOList ;
	}
	
	@Override
	public boolean getHistoryBoolean(int memberId) {
		
		// return에 true 들어가면 안되고 database에서 memberId로 된 data 있는지 조회해야됨
		int count = trainingHistoryMapper.countByMemberId(memberId);
        // 레코드가 존재하면 true, 없으면 false 반환
        return count > 0;
	}
	

	@Override
	public int createOMRcard(int memberId, List<Integer> problemIds) {

		// 회원 훈련 시작 시 OMR카드 생성
		int insertTrainingResponse = trainingResponseMapper.insertOMR(memberId, problemIds);
		log.info(insertTrainingResponse + "행 게시글 등록");
		
		return insertTrainingResponse;
	}

	@Override
	public int createHistory(int memberId, String[] partArray) {
		
		// 회원 훈련 시작 시 기록 생성
		int insertTrainingHistory = trainingHistoryMapper.insertHistory(memberId, partArray);
		log.info(insertTrainingHistory + "행 게시글 등록");
		
		return insertTrainingHistory;
	}

	// 채점 기능
	@Transactional(value = "transactionManager")
	@Override
	public List<TrainingRecordVO> getScore(int memberId) {

		// OMR 카드 가져오기
		List<TrainingResponseVO> responseList = trainingResponseMapper.getOMR(memberId); 
		
		// OMR 카드에서 problemId의 List 추출
	    List<Integer> problemIdList = responseList.stream()
	            .map(TrainingResponseVO::getProblemId)
	            .collect(Collectors.toList());
		
		// 정답지 가져오기
		List<CorrectOption> keyList = correctOptionMapper.getKeyList(problemIdList);
		
	    // keyList를 HashMap으로 변환 (문제ID -> CorrectOption)
	    Map<Integer, CorrectOption> keyMap = keyList.stream()
	            .collect(Collectors.toMap(CorrectOption::getProblemId, Function.identity()));
		
	    // 각 응답에 대해 정답과 비교하여 TrainingRecordVO 생성
	    List<TrainingRecordVO> recordList = new ArrayList<>();
	    for (TrainingResponseVO response : responseList) {
	        int problemId = response.getProblemId();
	        CorrectOption correctOption = keyMap.get(problemId);
	        
	        // 정답 비교: optionContent가 일치하면 scoring은 1, 틀리면 그대로 0
	        
	        int scoring = 0;
	        if (response.getOptionContent().equals(correctOption.getOptionContent())) {
	        	scoring = 1;
	        }
	        
	        // TrainingRecordVO 생성 (VO에 맞게 값들을 설정)
	        TrainingRecordVO record = new TrainingRecordVO();
	        record.setProblemId(problemId);
	        record.setMemberId(memberId);
	        record.setOptionContent(response.getOptionContent());
	        record.setScoring(scoring);
	        record.setProblemUncertain(response.getProblemUncertain());	        
	        
	        recordList.add(record);
	    }
	    
	    trainingResponseMapper.deleteByMemberId(memberId);
	    trainingHistoryMapper.deleteByMemberId(memberId);
	    
	    trainingRecordMapper.insert(recordList);
	    
		return recordList;
	}
	
	
//	@Override
//	public List<ProblemVO> getProblemByPart(String[] partArray, String[] yearArrayStr) {
//        
//		List<ProblemVO> problemVOList = new ArrayList<>();
//		
//		int[] yearArray = Arrays.stream(yearArrayStr)
//                				.mapToInt(Integer::parseInt)
//                				.toArray();
//		// problemPart와 problemYear로 List<Problem>을 가져오기
//        List<Problem> problems = problemMapper.selectProblemByPartAndYear(partArray, yearArray);
//        
//        log.info("problems : " + problems);
//        
//        // 문제가 없는 경우 빈 리스트 반환
//        if (problems == null || problems.isEmpty()) {
//            return new ArrayList<>();
//        }
//        
//        // 조회된 Problem 리스트에서 problemId만 추출
//        List<Integer> problemIdList = problems.stream()
//                                              .map(Problem::getProblemId)
//                                              .collect(Collectors.toList());
//        
//        // 추출한 problemId 목록을 이용해서 한 번에 해당하는 ProblemOption들과 첨부이미지를 조회
//        List<ProblemOption> options = problemOptionMapper.selectByProblemId(problemIdList);
//        log.info("options : " + options);
//        
//        List<AttachProblemVO> images = attachProblemMapper.selectByProblemId(problemIdList);
//        log.info("images : " + images);
//        
//        
//        // List<Problem> problems와 List<ProblemOption> options를 합하여 List<ProblemVO> problemList 만들기
//        // 문제와 해당 옵션을 합쳐 ProblemVO로 변환
//        
//        for (Problem problem : problems) {
//        	String optionContent = "";
//        	List<AttachProblemVO> attachList = new ArrayList<>();
//        	
//            for (ProblemOption option : options) {
//                if (problem.getProblemId() == option.getProblemId()) {
//                    // 문제와 옵션의 problemId가 일치하면 optionContent 설정
//                	optionContent = option.getOptionContent();
//                    // 한 문제에 하나의 옵션만 있으면 break; 추가 (필요시)
//                    break;
//                }
//            }
//            
//            for (AttachProblemVO image : images) {
//                if (problem.getProblemId() == image.getProblemId()) {
//                    // 문제와 옵션의 problemId가 일치하면 optionContent 설정
//                	attachList.add(image);
//                }
//            }
//            
//            ProblemVO vo = toVO(problem);
//            
//            vo.setOptionContent(optionContent);
//            vo.setAttachProblemList(attachList);
//            
//            // 생성한 ProblemVO 객체를 리스트에 추가
//            problemVOList.add(vo);
//
//        }
//                
//		return problemVOList ;
//	}
		
	
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
