package com.yesfuture.ex01.domain;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class AttachProblemVO {

	private int attachProblemId;
	private int problemId;
	private String attachProblemPath;
	private String attachProblemRealName;
	private String attachProblemChgName;
	private String attachProblemExtension;
	private Date attachProblemDateCreated;
	
}
