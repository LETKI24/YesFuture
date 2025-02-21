package com.yesfuture.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yesfuture.ex01.domain.TrainingRecordVO;

@Mapper
public interface TrainingRecordMapper {

	int insert(@Param("list") List<TrainingRecordVO> recordList);

}
