package com.yesfuture.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yesfuture.ex01.domain.AttachProblemVO;

@Mapper
public interface AttachProblemMapper {
    int insert(AttachProblemVO attachProblemVO);
    List<AttachProblemVO> selectByProblemId(@Param("problemIds") List<Integer> problemIds);
    AttachProblemVO selectByAttachProblemId(int attachProblemId);
	int insertModify(AttachProblemVO attachProblem);
	int delete(int ProblemId);
}
