package com.yesfuture.ex01.domain;

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
public class Problem {

	private int problemId;
	private String problemContent;
	private String problemDiagnosis;
	private int problemYear;
	private int problemSource;
	private String problemPart;
	private int memberId;
	
}
