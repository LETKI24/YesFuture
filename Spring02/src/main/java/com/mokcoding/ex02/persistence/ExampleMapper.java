package com.mokcoding.ex02.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper // Mapper 어노테이션
public interface ExampleMapper {
	
	@Select("SELECT SYSDATE FROM DUAL") // 현재시간 리턴해주는 쿼리 
	// 쿼리 결과를 메소드에 적용하는 어노테이션
	public String getDate(); // 메소드 리턴 타입은 쿼리 결과 데이터 타입과 매칭
}
