package com.yesfuture.ex01.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TrainingHistoryMapper {

	int insertHistory(@Param("memberId") int memberId, @Param("partArray") String[] partArray);

	int deleteByMemberId(int memberId);

	int countByMemberId(int memberId);

}
