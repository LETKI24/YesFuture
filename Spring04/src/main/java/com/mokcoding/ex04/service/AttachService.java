package com.mokcoding.ex04.service;

import java.util.List;

import com.mokcoding.ex04.domain.AttachVO;

public interface AttachService {
	
    int createAttach(AttachVO attachVO);
    AttachVO getAttachById(int attachId);
    List<Integer> getAllId();
    int updateAttach(AttachVO attachVO);
    int deleteAttach(int attachId);

}