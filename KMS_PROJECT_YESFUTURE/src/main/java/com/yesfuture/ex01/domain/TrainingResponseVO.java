package com.yesfuture.ex01.domain;

import java.util.Date;

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
public class TrainingResponseVO {
	
	private int trainingResponseId;
	private int problemId;
	private int memberId;
	private String optionContent;
	
	private boolean problemUncertain;
}
