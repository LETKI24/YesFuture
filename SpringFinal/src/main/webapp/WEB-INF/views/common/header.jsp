<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<header>
	<div class="header-container">
		<a href="../board/list">메인</a>
		<div class="header-section">
			<!-- 로그아웃 상태 -->
			<sec:authorize access="isAnonymous()">
			    <a href="../member/join">회원 가입</a>
			    <a href="../auth/login">로그인</a>
		    </sec:authorize>
		    <!-- 로그인 상태 -->
		    <sec:authorize access="isAuthenticated()">
		        <p><a href="../member/info"><sec:authentication property="principal.username"/></a>님</p>
		        <form action="../auth/logout" method="post">
		            <input type="submit" value="로그아웃">
		            <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
		        </form>
		    </sec:authorize>
		</div>
	</div>
</header>
