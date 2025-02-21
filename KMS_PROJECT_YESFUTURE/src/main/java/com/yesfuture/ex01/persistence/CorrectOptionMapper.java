package com.yesfuture.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yesfuture.ex01.domain.CorrectOption;

@Mapper
public interface CorrectOptionMapper {

	int insert(String correctOption);

	List<CorrectOption> getKeyList(List<Integer> problemIdList);

}
