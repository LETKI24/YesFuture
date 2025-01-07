package com.yesfuture.ex01.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString 
public class MemberRole {
	private int memberRoleId;
	private String memberEmail;
	private String memberRoleName;
}
