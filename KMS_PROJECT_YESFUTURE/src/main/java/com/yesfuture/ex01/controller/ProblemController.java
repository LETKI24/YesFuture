package com.yesfuture.ex01.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yesfuture.ex01.domain.CustomUser;
import com.yesfuture.ex01.domain.Member;
import com.yesfuture.ex01.domain.Problem;
import com.yesfuture.ex01.domain.ProblemVO;
import com.yesfuture.ex01.domain.TrainingRecordVO;
import com.yesfuture.ex01.service.ProblemService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value = "/problem") // url : /ex01/problem
@Log4j
public class ProblemController {
	
	@Autowired
	private ProblemService problemService;
	
	// problemMain.jsp 페이지 호출
	@GetMapping("/problemMain")
	public void problemMain(@AuthenticationPrincipal CustomUser customUser,
			   				Model model) {
		log.info("problemMain()");
				
		boolean existHistory = problemService.getHistoryBoolean(customUser.getMember().getMemberId());
		
		model.addAttribute("existHistory", existHistory);
	
	}
	
	// registerProblem.jsp 페이지 호출
	@GetMapping("/registerProblem")
	public void register() {
		log.info("register()");
	}
	
	@PostMapping("/registerProblem")
	public String registerQuestionPOST(ProblemVO problemVO, RedirectAttributes reAttr,
									@RequestParam("optionContentAll") String optionContentAll) {
		log.info("registerQuestionPOST()");
		log.info("problemVO = " + problemVO.toString());
		log.info("optionContentAll = " + optionContentAll);
		
		String[] optionArray = optionContentAll.split("\\|");
		log.info("optionArray : " + Arrays.toString(optionArray));
		
		int result = problemService.createProblem(problemVO, optionArray);
		log.info(result + "행 등록");
		return "redirect:/board/info";
	}
	
    @GetMapping("/trainingEntrance")
    public String trainingEntrance(@RequestParam("part") String part,
                                   @RequestParam("year") String year,
                                   Model model) {
        // "part"와 "year"는 각각 "IDIOT,LAW"와 "2022,2023,2024"와 같이 comma로 구분된 문자열로 들어옵니다.
        // 필요한 경우 이를 배열이나 List로 변환할 수 있습니다.
        String[] partArray = part.split(",");
        String[] yearArray = year.split(",");
        
        List<Integer> problemIdList = problemService.getProblemCount(partArray, yearArray);
        
        log.info("problemIdList : " + problemIdList);
        
        model.addAttribute("problemIdList", problemIdList);
        
        // trainingEntrance.jsp (View Resolver에 따라 JSP로 포워딩)
        return "problem/trainingEntrance";
    }
    
    @GetMapping("/training")
    public String training(@RequestParam("problemIds") List<String> StrProblemIds,
    					   @RequestParam("problemParts") String StrProblemParts,
    					   @AuthenticationPrincipal CustomUser customUser,
    					   Model model) {
        log.info("training()");
        
        int memberId = customUser.getMember().getMemberId();
        String[] partArray = StrProblemParts.split(",");
        
        // problemId 리스트를 기반으로 전체 문제 데이터 조회
        List<Integer> problemIds = StrProblemIds.stream()
							                .map(Integer::parseInt)
							                .collect(Collectors.toList());

        problemService.createOMRcard(memberId, problemIds);
        problemService.createHistory(memberId, partArray);
            	
        List<ProblemVO> problemList = problemService.getProblemByIds(problemIds);

        log.info("problemList : " + problemList);
        
        model.addAttribute("problemList", problemList);
        
        return "problem/training"; // training.jsp로 포워딩
    }
    
    @GetMapping("/trainingResult")
    public String TrainingResult(@AuthenticationPrincipal CustomUser customUser,
    							 Model model) {
        
    	List<TrainingRecordVO> trainingRecordList = 
    							problemService.getScore(customUser.getMember().getMemberId());
    	
    	log.info("trainingRecordList : " + trainingRecordList);
    	
        // 채점 결과를 모델에 담아서 JSP에 전달
        model.addAttribute("trainingRecordList", trainingRecordList);
        
        // 최종 결과를 보여줄 JSP 페이지 (예: trainingResult.jsp)
        return "problem/trainingResult";
    }

    
}
