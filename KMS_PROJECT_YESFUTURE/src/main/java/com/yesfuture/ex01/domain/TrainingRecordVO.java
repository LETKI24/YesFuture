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
public class TrainingRecordVO {
	
	private int trainingRecordId;
	private int memberId;
	private int problemId;
	private Date trainingDate;
	
	private int scoring; // 1은 맞춘 문제, 0은 틀린 문제
	private String optionContent;
	private int problemUncertain; // 1은 애매했던 문제, 0은 안애매함
}
