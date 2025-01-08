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
public class AttachQuestion {
	
	private int attachQuestionId;
	private int boardQuestionId;
	private String attachQuestionPath;
	private String attachQuestionRealName;
	private String attachQuestionChgName;
	private String attachQuestionExtension;
	private Date attachQuestionDateCreated;
	
}
