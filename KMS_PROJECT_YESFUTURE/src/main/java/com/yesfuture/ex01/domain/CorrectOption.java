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
public class CorrectOption {
	private int correctOptionId;
	private int problemId;
	private String optionContent;
}
