package com.mokcoding.mysite.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mokcoding.mysite.domain.Reply;

@Mapper
public interface ReplyMapper {
	int insert(Reply reply);
	List<Reply> selectListByBoardId(int boardId);
	int update(Reply reply);
	int delete(int replyId);
}
