<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<!-- jquery 라이브러리 import -->
<script src="https://code.jquery.com/jquery-3.7.1.js">
</script>

<style type="text/css">
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
        
        /* 전체 레이아웃 */
        .container {
            display: flex;
            flex-direction: column; /* 세로 방향 정렬 */
            height: 80vh; /* 화면 높이의 80%를 사용 */
            padding: 10px;
        }
        			
		table, th, td {
			border-style: solid;
			border-width: 1px;
			text-align: center;
		}
		
		ul {
			list-style-type: none;
			text-align: center;
		}
		
		li {
			display: inline-block;
		}
</style>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>질문 게시판</h1>

	<div class="menu-bar">
        <!-- 이 위치에 main으로 가는 버튼 만들기 -->
        <a href="../../problem/problemMain" class="group1"><input type="button" value="문제은행 가기"></a>
        <a href="../boardMain" class="group1"><input type="button" value="게시판"></a>
	
		<div class="group2">
		    <!-- 로그인 상태 -->
		    <sec:authorize access="isAuthenticated()">
		        <a href="../../board/info"><sec:authentication property="principal.member.memberNickname"/></a>
		        <form action="../auth/logout" method="post">
		            <input type="submit" value="로그아웃">
		            <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
		        </form>
		    </sec:authorize>
		</div>
	</div>
	
	<!-- 가로 경계선 -->
    <hr class="divider">
    
    	<!-- 메인 레이아웃 -->
    <div class="container">
    	<table>
			<thead>
				<tr>
					<th style="width: 60px">번호</th>
					<th style="width: 700px">제목</th>
					<th style="width: 120px">작성자</th>
					<th style="width: 100px">작성일</th>
					<th style="width: 100px">댓글</th>
					<th style="width: 100px">조회</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="BoardQuestionVO" items="${boardQuestionList }">
					<tr>
						<td>${BoardQuestionVO.boardQuestionId }</td>
						<td><a href="boardQuestionDetail?boardQuestionId=${BoardQuestionVO.boardQuestionId }">
						${BoardQuestionVO.boardQuestionTitle }</a></td>
						<td>${BoardQuestionVO.memberNickname }</td>
						<!-- boardDateCreated 데이터 포멧 변경, 원하는 형태로 데이터를 바꿀 수 있다. -->
						<fmt:formatDate value="${BoardQuestionVO.boardQuestionDateCreated }"
							pattern="yyyy-MM-dd HH:mm:ss" var="boardQuestionDateCreated" />
						<td>${boardQuestionDateCreated }</td>
						<td>${BoardQuestionVO.boardQuestionReplyCount }</td>
						<td>${BoardQuestionVO.boardQuestionHitCount }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	
		<ul>
			<!-- 이전 버튼 생성을 위한 조건문 -->
			<c:if test="${pageMaker.isPrev() }">
				<li><a href="boardQuestion?pageNum=${pageMaker.startNum - 1}">이전</a></li>
			</c:if>
			<!-- 반복문으로 시작 번호부터 끝 번호까지 생성 -->
			<c:forEach begin="${pageMaker.startNum }"
				end="${pageMaker.endNum }" var="num">
				<li><a href="boardQuestion?pageNum=${num }">${num }</a></li>
			</c:forEach>
			<!-- 다음 버튼 생성을 위한 조건문 -->
			<c:if test="${pageMaker.isNext() }">
				<li><a href="boardQuestion?pageNum=${pageMaker.endNum + 1}">다음</a></li>
			</c:if>
		</ul>
		
		<hr>
		
		<ul>
			<!-- 글쓰기 버튼 -->
			<a href="registerQuestion"><input type="button" value="글쓰기"></a>
		</ul>
		
		<ul>
			<!-- 검색 -->
			<select id="searchForWhat" name="searchForWhat">
				<option value="boardQuestionTitle">제목</option>
				<option value="memberNickname">작성자</option>
			</select>
			<input type="text" id="searchQuestion">
			<button id="btnSearch">검색</button>
		</ul>
		
    </div>
    
    <script type="text/javascript">
    	
    	
    	
    </script>
    
</body>
</html>