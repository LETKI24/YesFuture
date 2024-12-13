package com.mokcoding.ex01.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Lombok 태그들
@NoArgsConstructor // 기본 생성자
@Getter // getter 메소드
@Setter // setter 메소드
@ToString // toString 메소드
public class InfoVO {
	private String name; // parameter name = "name"   -> info.jsp 참조
	private String email; // parameter name = "email"
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDate; // parameter name = "birthDate"
	// 이렇게만 하면 BindException이 뜸, 21번째 줄에 @DateTimeFormat 추가해야 함
}
