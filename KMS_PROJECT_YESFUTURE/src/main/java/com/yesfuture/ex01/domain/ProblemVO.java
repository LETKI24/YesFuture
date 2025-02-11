package com.yesfuture.ex01.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProblemVO {

	private int problemId;
	private String problemContent;
	private String problemDiagnosis;
	private int problemYear;
	private int problemSource;
	private String problemPart;
	private int memberId;

	private List<AttachProblemVO> attachProblemList;
	
	public List<AttachProblemVO> getAttachProblemList() {
		if(attachProblemList == null) {
			attachProblemList = new ArrayList<AttachProblemVO>();
		}
		return attachProblemList;
	}
	
	private List<ProblemOption> optionList;
	
	public List<ProblemOption> getOptionList() {
		if(optionList == null) {
			optionList = new ArrayList<ProblemOption>();
		}
		return optionList;
	}
	
}
