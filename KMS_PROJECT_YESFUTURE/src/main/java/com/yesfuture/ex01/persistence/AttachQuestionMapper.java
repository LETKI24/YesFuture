package com.yesfuture.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yesfuture.ex01.domain.AttachQuestion;

@Mapper
public interface AttachQuestionMapper {
    int insert(AttachQuestion attachQuestion);
    List<AttachQuestion> selectByBoardQuestionId(int boardQuestionId);
    AttachQuestion selectByAttachQuestionId(int attachQuestionId);
	int insertModify(AttachQuestion attachQuestion);
	int delete(int boardQuestionId);
}