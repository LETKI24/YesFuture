package com.yesfuture.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yesfuture.ex01.domain.Problem;

@Mapper
public interface ProblemMapper {

	int insert(Problem problem);
	
//    List<Problem> selectProblemByPartAndYear(@Param("partArray") String[] partArray, 
//            									@Param("yearArray") int[] yearArray);
    
    List<Integer> selectProblemCount(@Param("partArray") String[] partArray, 
										@Param("yearArray") int[] yearArray);

	List<Problem> selectProblemByIds(List<Integer> problemIds);
}
