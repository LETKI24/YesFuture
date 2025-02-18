package com.yesfuture.ex01.domain;

import java.util.Date;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TrainingRecordWrongVO {

	private int trainingRecordWrongId;
	private int memberId;
	private int problemId;
	private Date trainTime;
}
