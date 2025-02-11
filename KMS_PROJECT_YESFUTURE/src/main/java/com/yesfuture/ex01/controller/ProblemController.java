package com.yesfuture.ex01.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yesfuture.ex01.domain.Problem;
import com.yesfuture.ex01.domain.ProblemVO;
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
	public void boardMain() {
		log.info("problemMain()");
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
    
    @PostMapping("/training")
    public String training(@RequestParam("problemIds") List<String> StrProblemIds, Model model) {
        log.info("training()");
        
        List<Integer> problemIds = StrProblemIds.stream()
							                .map(Integer::parseInt)
							                .collect(Collectors.toList());
        
    	// problemId 리스트를 기반으로 전체 문제 데이터 조회
        List<ProblemVO> problemList = problemService.getProblemByIds(problemIds);

        log.info("problemList : " + problemList);
        
        model.addAttribute("problemList", problemList);
        
        return "problem/training"; // training.jsp로 포워딩
    }
}
