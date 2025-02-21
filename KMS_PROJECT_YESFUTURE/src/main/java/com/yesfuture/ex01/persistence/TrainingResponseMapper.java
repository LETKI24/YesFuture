package com.yesfuture.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yesfuture.ex01.domain.TrainingResponseVO;

@Mapper
public interface TrainingResponseMapper {

	int insertOMR(@Param("memberId") int memberId, @Param("problemIds") List<Integer> problemIds);

	int update(TrainingResponseVO trainingResponseVO);

	List<TrainingResponseVO> getOMR(int memberId);

	int deleteByMemberId(int memberId);

}
