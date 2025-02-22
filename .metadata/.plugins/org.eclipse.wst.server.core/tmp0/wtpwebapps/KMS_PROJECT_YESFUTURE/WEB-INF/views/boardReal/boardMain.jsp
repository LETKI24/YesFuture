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
            height: 80vh; /* 화면 높이의 80%를 사용 */
            padding: 10px;
        }

        /* 왼쪽 메뉴 영역 */
        .left-menu {
            flex: 1; /* 1:4 비율 중 1 */
            display: flex;
            flex-direction: column;
            align-items: center; /* 버튼을 중앙 정렬 */
            border-right: 1px solid black; /* 세로 구분선 */
            padding: 20px;
        }

        /* 버튼 스타일 */
        .left-menu button {
            width: 100px;
            margin: 10px 0;
            padding: 10px;
            font-size: 16px;
            cursor: pointer;
            border: none;
            background-color: #4CAF50;
            color: white;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .left-menu button:hover {
            background-color: #45a049;
        }

        /* 오른쪽 게시판 출력 영역 */
        .right-board {
            flex: 4; /* 1:4 비율 중 4 */
            padding: 20px;
            overflow-y: auto; /* 내용이 넘칠 경우 스크롤 */
            background-color: #f9f9f9;
        }

        /* 게시판 제목 스타일 */
        .board-title {
            font-size: 24px;
            margin-bottom: 20px;
            border-bottom: 2px solid #000;
            padding-bottom: 10px;
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
	<h1>게시판</h1>
	
	<div class="menu-bar">
        <!-- 이 위치에 main으로 가는 버튼 만들기 -->
        <a href="../problem/problemMain" class="group1"><input type="button" value="문제은행 가기"></a>
        <a href="boardMain" class="group1"><input type="button" value="게시판"></a>
	
		<div class="group2">
		    <!-- 로그인 상태 -->
		    <sec:authorize access="isAuthenticated()">
		        <a href="../board/info"><sec:authentication property="principal.member.memberNickname"/></a>
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
        <!-- 왼쪽 메뉴 영역 -->
        <div class="left-menu">
            <button id="question">질문</button>
            <button id="free">자유</button>
            <button id="propose">건의</button>
        </div>

        <!-- 오른쪽 게시판 영역 -->
        <div class="right-board" id="boardQuestionArea">
        	<!-- 게시판 제목 -->
    		<div style="display: flex; align-items: center;">
				<h2 style="margin-bottom: 10; margin-right: 20px;">질문게시판</h2>
				<a href="boardQuestion/boardQuestion"><input type="button" value="더보기"></a>
			</div>
    		
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
					<c:forEach var="BoardQuestionVO" items="${boardQuestionListMinimal }">
						<tr>
							<td>${BoardQuestionVO.boardQuestionId }</td>
							<td><a href="boardQuestion/boardQuestionDetail?boardQuestionId=${BoardQuestionVO.boardQuestionId }">
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
        </div>
        
    </div>
    
        	
    <script type="text/javascript">
    $(document).ready(function() {
    	
    	// 질문 버튼 클릭 이벤트
    	$("#question").click(function() {
            loadBoardQuestion();
        });
    	
        // 자유 버튼 클릭 이벤트
        $("#free").click(function() {
            loadBoardFree();
        });

        // 건의 버튼 클릭 이벤트
        $("#propose").click(function() {
            loadBoardPropose();
        });
        
        
        
        // 비동기 데이터 로드 함수
        function loadBoardPropose() {
        	var tableProposeHTML = ''; // 각 테이블 데이터를 HTML에 표현할 문자열 변수
        	
            $.ajax({
                url: "../boardReal/proposeMinimal",        // 서버 요청 URL
                type: "GET",       // 요청 방식
                dataType: "json",  // 응답 데이터 타입
                success: function(result) {
                    tableProposeHTML += '<div style="display: flex; align-items: center;">' 
                    	+ '<h2 style="margin-bottom: 15; margin-right: 20px;">건의게시판</h2>'
                    	+ '<a href="/boardPropose/boardPropose"><input type="button" value="더보기"></a>'
                    	+ '</div>'
                    	+ '<table>'
                        + '<thead>'
                        + '<tr>'
                        + '<th>번호</th>'
                        + '<th>제목</th>'
                        + '<th>작성자</th>'
                        + '<th>작성일</th>'
                        + '<th>댓글</th>'
                        + '<th>조회</th>'
                        + '</tr>'
                        + '</thead>'
                        + '<tbody>';

                    // 데이터 반복
                    $.each(result, function(index, item) {

                    	// 전송된 DateCreated는 문자열 형태이므로 날짜 형태로 변환이 필요
                    	var boardProposeDateCreated = new Date(item.boardProposeDateCreated);
                    	
                        tableProposeHTML += '<tr>'
                                + '<td>' + item.boardProposeId + '</td>'
                                + '<td><a href="boardProposeDetail?boardProposeId=' + item.boardProposeId + '">'
                                + item.boardProposeTitle + '</a></td>'
                                + '<td>' + item.memberNickname + '</td>'
                                + '<td>' + boardProposeDateCreated + '</td>'
                                + '<td>' + item.boardProposeReplyCount + '</td>'
                                + '<td>' + item.boardProposeHitCount + '</td>'
                                + '</tr>';
                    });

                    tableProposeHTML += '</tbody>'
                    			+ '</table>';

                    // 기존 테이블 영역을 교체
                    $("#boardQuestionArea").html(tableProposeHTML);
                }, // end function(result)
                error: function() {
                    alert("데이터를 불러오는 데 실패했습니다.");
                }
            }); // end ajax
        } // end loadBoardPropose()
        
                
     	// 비동기 데이터 로드 함수
        function loadBoardQuestion() {
        	var tableQuestionHTML = ''; // 각 테이블 데이터를 HTML에 표현할 문자열 변수
        	
            $.ajax({
                url: "../boardReal/questionMinimal",        // 서버 요청 URL
                type: "GET",       // 요청 방식
                dataType: "json",  // 응답 데이터 타입
                success: function(result) {
                    tableQuestionHTML += '<div style="display: flex; align-items: center;">' 
                    	+ '<h2 style="margin-bottom: 15; margin-right: 20px;">질문게시판</h2>'
                    	+ '<a href="boardQuestion/boardQuestion"><input type="button" value="더보기"></a>'
                    	+ '</div>'
                    	+ '<table>'
                        + '<thead>'
                        + '<tr>'
                        + '<th>번호</th>'
                        + '<th>제목</th>'
                        + '<th>작성자</th>'
                        + '<th>작성일</th>'
                        + '<th>댓글</th>'
                        + '<th>조회</th>'
                        + '</tr>'
                        + '</thead>'
                        + '<tbody>';

                    // 데이터 반복
                    $.each(result, function(index, item) {

                    	// 전송된 DateCreated는 문자열 형태이므로 날짜 형태로 변환이 필요
                    	var boardQuestionDateCreated = new Date(item.boardQuestionDateCreated);
                    	
                        tableQuestionHTML += '<tr>'
                                + '<td>' + item.boardQuestionId + '</td>'
                                + '<td><a href="boardQuestion/boardQuestionDetail?boardQuestionId=' + item.boardQuestionId + '">'
                                + item.boardQuestionTitle + '</a></td>'
                                + '<td>' + item.memberNickname + '</td>'
                                + '<td>' + boardQuestionDateCreated + '</td>'
                                + '<td>' + item.boardQuestionReplyCount + '</td>'
                                + '<td>' + item.boardQuestionHitCount + '</td>'
                                + '</tr>';
                    });

                    tableQuestionHTML += '</tbody>'
                    			+ '</table>';

                    // 기존 테이블 영역을 교체
                    $("#boardQuestionArea").html(tableQuestionHTML);
                }, // end function(result)
                error: function() {
                    alert("데이터를 불러오는 데 실패했습니다.");
                }
            }); // end ajax
        } // end loadBoardQuestion()
        
     	// 비동기 데이터 로드 함수
        function loadBoardFree() {
        	var tableFreeHTML = ''; // 각 테이블 데이터를 HTML에 표현할 문자열 변수
        	
            $.ajax({
                url: "../boardReal/freeMinimal",        // 서버 요청 URL
                type: "GET",       // 요청 방식
                dataType: "json",  // 응답 데이터 타입
                success: function(result) {
                    tableFreeHTML += '<div style="display: flex; align-items: center;">' 
                    	+ '<h2 style="margin-bottom: 15; margin-right: 20px;">자유게시판</h2>'
                    	+ '<a href="/boardFree/boardFree"><input type="button" value="더보기"></a>'
                    	+ '</div>'
                    	+ '<table>'
                        + '<thead>'
                        + '<tr>'
                        + '<th>번호</th>'
                        + '<th>제목</th>'
                        + '<th>작성자</th>'
                        + '<th>작성일</th>'
                        + '<th>댓글</th>'
                        + '<th>조회</th>'
                        + '<th>좋아요</th>'
                        + '</tr>'
                        + '</thead>'
                        + '<tbody>';

                    // 데이터 반복
                    $.each(result, function(index, item) {

                    	// 전송된 DateCreated는 문자열 형태이므로 날짜 형태로 변환이 필요
                    	var boardFreeDateCreated = new Date(item.boardFreeDateCreated);
                    	
                        tableFreeHTML += '<tr>'
                                + '<td>' + item.boardFreeId + '</td>'
                                + '<td><a href="boardFreeDetail?boardFreeId=' + item.boardFreeId + '">'
                                + item.boardFreeTitle + '</a></td>'
                                + '<td>' + item.memberNickname + '</td>'
                                + '<td>' + boardFreeDateCreated + '</td>'
                                + '<td>' + item.boardFreeReplyCount + '</td>'
                                + '<td>' + item.boardFreeHitCount + '</td>'
                                + '<td>' + item.boardFreeLikeCount + '</td>'
                                + '</tr>';
                    });

                    tableFreeHTML += '</tbody>'
                    			+ '</table>';

                    // 기존 테이블 영역을 교체
                    $("#boardQuestionArea").html(tableFreeHTML);
                }, // end function(result)
                error: function() {
                    alert("데이터를 불러오는 데 실패했습니다.");
                }
            }); // end ajax
        } // end loadBoardFree()
        
    }); // end document  
    </script>
	    
</body>
</html>