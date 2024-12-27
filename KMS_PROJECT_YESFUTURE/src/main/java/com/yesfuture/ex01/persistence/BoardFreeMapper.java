package com.yesfuture.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yesfuture.ex01.domain.BoardFreeVO;

@Mapper
public interface BoardFreeMapper {
	List<BoardFreeVO> getBoardFreeListMinimal();
}
