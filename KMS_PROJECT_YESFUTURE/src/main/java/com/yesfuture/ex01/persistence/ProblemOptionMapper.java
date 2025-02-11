package com.yesfuture.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yesfuture.ex01.domain.ProblemOption;

@Mapper
public interface ProblemOptionMapper {
	
	int insert(String options);

	List<ProblemOption> selectByProblemId(@Param("problemIds") List<Integer> problemIds);
	
}
