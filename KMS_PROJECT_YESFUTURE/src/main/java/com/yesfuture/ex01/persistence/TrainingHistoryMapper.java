package com.yesfuture.ex01.persistence;

import org.apache.ibatis.annotations.Param;

public interface TrainingHistoryMapper {

	int insertHistory(@Param("memberId") int memberId, @Param("partArray") String[] partArray);

}
