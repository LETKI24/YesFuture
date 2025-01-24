<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
    <style>
    
    	/* 경계선 스타일 */
        .divider {
            border: none;
            border-top: 1px solid #000; /* 얇은 검은색 선 */
            margin: 0;
        }
        
        /* 메뉴바 스타일 */
        .menu-bar {
            display: flex; /* Flexbox 적용 */
            justify-content: flex-start; /* 왼쪽부터 가로로 배치 */
            align-items: center; /* 세로 중앙 정렬 */
            padding: 10px 20px; /* 메뉴바 안쪽 여백 */
        }
        
        /* 좌측 버튼 스타일 */
		.group1 {
		    display: flex;
		}
		
		/* 우측 버튼 그룹 스타일 */
		.group2 {
		    display: flex;
		    margin-left: auto; /* 우측으로 밀어붙임 */
		}
	    
        /* 버튼 사이의 간격 설정 */
        .menu-bar a {
            margin-right: 20px; /* 각 버튼 사이의 간격 (원하는 값으로 조정 가능) */
            text-decoration: none; /* 링크 밑줄 제거 */
        }

        /* 버튼 스타일 (옵션) */
        input[type="button"] {
            font-size: 16px;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        
        /* 특정 value (닉네임 버튼)에 스타일 적용 */
    	input[type="button"][value="${sessionScope.memberNickname}"] {
        background-color: transparent; /* 배경을 투명하게 설정 */
        color: #4CAF50; /* 글자 색상 설정 */
        border: 1px solid #4CAF50; /* 선택사항: 테두리 추가 */
    	}

        /* 버튼에 마우스를 올렸을 때 스타일 (옵션) */
        input[type="button"]:hover {
            background-color: #45a049;
        }
    </style>

<meta charset="UTF-8">
<title>YesFuture 메인 페이지</title>
</head>
<body>
	<h1>예스 퓨처~</h1>
	
	<sec:authentication property="principal" var="user"/>
	
	<div class="menu-bar">
        <!-- 이 위치에 main으로 가는 버튼 만들기 -->
        <a href="../problem/problemMain" class="group1"><input type="button" value="문제은행 가기"></a>
        <a href="../boardReal/boardMain" class="group1"><input type="button" value="게시판"></a>
	
		<div class="group2">
			<!-- 로그아웃 상태 -->
			<sec:authorize access="isAnonymous()">
			    <a href="agree">회원 가입</a>
			    <a href="../auth/login">로그인</a>
		    </sec:authorize>
		    <!-- 로그인 상태 -->
		    <sec:authorize access="isAuthenticated()">
		        <a href="info"><sec:authentication property="principal.member.memberNickname"/></a>
		        <form action="../auth/logout" method="post">
		            <input type="submit" value="로그아웃">
		            <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
		        </form>
		    </sec:authorize>
		</div>
	</div>
	
	<!-- 가로 경계선 -->
    <hr class="divider">
	
	<div style="display: flex; justify-content: center; align-items: center; gap: 20px; margin-top: 100px;">
    <!-- 첫 번째 이미지 -->
    <img src="${pageContext.request.contextPath}/resources/images/레인지로버.png" 
         alt="레인지로버 이미지" 
         style="width: 45%; height: auto;" />
         
    <!-- 두 번째 이미지 -->
    <img src="${pageContext.request.contextPath}/resources/images/뽀르쉐.webp" 
         alt="뽀르쉐 이미지" 
         style="width: 45%; height: auto;" />
	</div>
	

</body>
</html>