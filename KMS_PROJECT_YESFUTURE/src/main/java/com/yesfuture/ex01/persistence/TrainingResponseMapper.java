package com.yesfuture.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yesfuture.ex01.domain.TrainingResponseVO;

public interface TrainingResponseMapper {

	int insertOMR(@Param("memberId") int memberId, @Param("problemIds") List<Integer> problemIds);

	int update(TrainingResponseVO trainingResponseVO);

}
