<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>

	<!-- jquery 라이브러리 import -->
	<script src="https://code.jquery.com/jquery-3.7.1.js">
	</script>

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
        
        body {
            font-family: Arial, sans-serif;
		    margin: 0;
		    padding: 0;
		    background-color: #f4f4f4;
        }
        .button-part {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 20px;
            width: 80%;
            max-width: 800px;
            margin: 100px auto; /* 수직 여백 추가 */
        }
        .button-year {
            display: grid;
            grid-template-columns: repeat(5, 1fr);
            gap: 20px;
            width: 80%;
            max-width: 800px;
            margin: 100px auto; /* 수직 여백 추가 */
        }
        .button {
            background-color: #fff;
            border: 2px solid #ddd;
            border-radius: 10px;
            text-align: center;
            padding: 20px;
            cursor: pointer;
            transition: 0.3s;
            position: relative;
        }
        .button.checked {
            background-color: #e0f7e0;
            border-color: #34a853;
        }
        .button img {
            max-width: 40px;
            margin-bottom: 10px;
        }
        .button span {
            display: block;
            font-size: 14px;
            color: #333;
        }
        .check-icon {
            position: absolute;
            top: 10px;
            right: 10px;
            font-size: 18px;
            color: #34a853;
            display: none;
        }
        .button.checked .check-icon {
            display: block;
        }
        
        .hidden {
            display: none;
        }

        .results {
            margin: 20px auto;
            width: 80%;
            max-width: 800px;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        
    </style>
<meta charset="UTF-8">
<title>문제은행 메인</title>
</head>
<body>
	<h1>문제은행 만들기</h1>
	
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
    
	<div class="button-part">
        <div class="button" data-value="IMD" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <%-- img src="icons/family.png" alt="가정의학과" 추후 img 업데이트, 앞뒤로 <>만 추가 --%>
            <span>감염내과</span>
        </div>
        <div class="button" data-value="IME" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>내분비내과</span>
        </div>
        <div class="button" data-value="IMR" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>류마티스내과</span>
        </div>
        <div class="button" data-value="MINOR1" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>마이너 - 비뇨의학과, 신경과, 신경외과</span>
        </div>
        <div class="button" data-value="MINOR2" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>마이너 - 응급의학과, 이비인후과, 정형외과, 피부과</span>
        </div>
        <div class="button" data-value="GY" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>부인과</span>
        </div>
        <div class="button" data-value="OB" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>산과</span>
        </div>
        <div class="button" data-value="PG" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>소아과 - 총론</span>
        </div>
        <div class="button" data-value="P1" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>소아과 - 감염, 골격계, 내분비, 면역학, 비뇨기, 소화기</span>
        </div>
        <div class="button" data-value="P2" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>소아과 - 순환기, 신경계, 알레르기, 종양, 혈액, 호흡기</span>
        </div>
        <div class="button" data-value="IMG" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>소화기내과</span>
        </div>
        <div class="button" data-value="IMC" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>순환기내과</span>
        </div>
        <div class="button" data-value="IMN" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>신장내과</span>
        </div>
        <div class="button" data-value="IMA" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>알레르기내과</span>
        </div>
        <div class="button" data-value="IDIOT" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>예방의학</span>
        </div>
        <div class="button" data-value="LAW" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>의료법규</span>
        </div>
        <div class="button" data-value="GS1" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>외과 - 간담췌, 내분비, 유방, 소화기</span>
        </div>
        <div class="button" data-value="GS2" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>외과 - 소아, 총론, 혈관</span>
        </div>
        <div class="button" data-value="PSY" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>정신과</span>
        </div>
        <div class="button" data-value="IMO" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>종양내과</span>
        </div>
        <div class="button" data-value="IMH" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>혈액내과</span>
        </div>
        <div class="button" data-value="IMP" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>호흡기내과</span>
        </div>
        
    </div>
    
    <div class="button-year">
        <div class="button" data-value="2024" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <%-- img src="icons/family.png" alt="가정의학과" 추후 img 업데이트, 앞뒤로 <>만 추가 --%>
            <span>2024년</span>
        </div>
        <div class="button" data-value="2023" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>2023년</span>
        </div>
        <div class="button" data-value="2022" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>2022년</span>
        </div>
        <div class="button" data-value="2021" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>2021년</span>
        </div>
        <div class="button" data-value="2020" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>2020년</span>
        </div>
        <div class="button" data-value="2019" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>2019년</span>
        </div>
        <div class="button" data-value="2018" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>2018년</span>
        </div>
        <div class="button" data-value="2017" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>2017년</span>
        </div>
        <div class="button" data-value="2016" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>2016년</span>
        </div>
        <div class="button" data-value="2015" onclick="toggleCheck(this)">
            <span class="check-icon"></span>
            <span>2015년</span>
        </div>        
    </div>
    
    <div>
    	<button id="submit-btn" style="display: block; margin: 20px auto; padding: 10px 20px; 
    				font-size: 16px; cursor: pointer;">문제풀기</button>
    </div>
	
	<div id="results" class="results hidden">
        <h3>선택한 파트</h3>
        <ul id="results-list"></ul>
    </div>
	
    <script>
        // 버튼 클릭 시 선택 상태를 토글하는 함수
        function toggleCheck(button) {
            button.classList.toggle('checked'); // 선택 상태 추가/제거
        }
        

        // '문제풀기' 버튼 클릭 시 선택된 분과와 년도를 수집해 trainingEntrance.jsp로 이동
	    document.getElementById("submit-btn").addEventListener("click", function() {
	        // 선택된 분과 버튼을 모두 찾음
	        var partButton = document.querySelectorAll(".button-part .button.checked");
	        var selectedPart = [];
	        partButton.forEach(function(btn) {
	            selectedPart.push(btn.getAttribute("data-value"));
	        });
	        
	        // 선택된 년도 버튼을 모두 찾음
	        var yearButton = document.querySelectorAll(".button-year .button.checked");
	        var selectedYear = [];
	        yearButton.forEach(function(btn) {
	            selectedYear.push(btn.getAttribute("data-value"));
	        });
	        
	        console.log("selectedPart : " + selectedPart);
	        console.log("selectedYear : " + selectedYear);
	            
	        // 분과와 년도 모두 최소 한 개씩 선택되어야 함
	        if (selectedPart.length === 0 || selectedYear.length === 0) {
	            alert("분과와 출제년도를 모두 선택해주세요.");
	            return;
	        }
	            
	        // 예시로, 분과와 년도 값을 콤마로 구분한 문자열로 전달
	        // URL 예: trainingEntrance.jsp?dept=IDIOT,LAW&year=2022,2023,2024
	        var queryString = "?part=" + encodeURIComponent(selectedPart.join(",")) +
	                          "&year=" + encodeURIComponent(selectedYear.join(","));
	        
	        // trainingEntrance.jsp로 이동
	        window.location.href = "trainingEntrance.jsp" + queryString;
        });
    </script>
    
</body>
</html>