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
public class TrainingHistoryVO {
	
	private int trainingHistoryId;
	private int memberId;
	private String problemPart;
	private Date trainingHistoryDateCreated;
	
}
