<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin</title>
</head>
<body>    
	<h2>ADMIN 권한만 접근 가능한 admin 페이지</h2>
	
	<h2>회원 정보</h2>
	
	<!-- principal = loadUserByUsername() 메소드의 UserDetails 객체 정보를 갖고 있음, 여기서 정보가 출력된다 -->
	<p>principal : <sec:authentication property="principal"/></p>
	<p>회원 아이디 : <sec:authentication property="principal.member.memberId"/></p>
	<p>회원 비밀번호 : <sec:authentication property="principal.member.memberPw"/> </p>
	<p>회원 이름 : <sec:authentication property="principal.member.memberName"/></p>
	<p>회원 등록일 : <sec:authentication property="principal.member.regDate"/></p>
    <p>권한 리스트 : <sec:authentication property="principal.authorities"/></p>
	
	<form action="../access/logout" method="POST">
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
		<input type="submit" value="로그아웃">
	</form>
</body>
</html>