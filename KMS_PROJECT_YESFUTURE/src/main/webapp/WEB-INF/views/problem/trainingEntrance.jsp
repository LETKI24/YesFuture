<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>

<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

    <!-- 사용자가 선택한 파트와 년도 출력 -->
    <p>
        회원님이 선택한 파트는 
        <c:forEach var="p" items="${fn:split(param.part, ',')}" varStatus="status">
            <!-- 코드 값을 실제 표시할 이름으로 변환 -->
			<c:choose>
			    <c:when test="${p eq 'IDIOT'}">예방의학</c:when>
			    <c:when test="${p eq 'LAW'}">의료법규</c:when>
			    <c:when test="${p eq 'IMD'}">감염내과</c:when>
			    <c:when test="${p eq 'IME'}">내분비내과</c:when>
			    <c:when test="${p eq 'IMR'}">류마티스내과</c:when>
			    <c:when test="${p eq 'MINOR1'}">마이너 - 비뇨의학과, 신경과, 신경외과</c:when>
			    <c:when test="${p eq 'MINOR2'}">마이너 - 응급의학과, 이비인후과, 정형외과, 피부과</c:when>
			    <c:when test="${p eq 'GY'}">부인과</c:when>
			    <c:when test="${p eq 'OB'}">산과</c:when>
			    <c:when test="${p eq 'PG'}">소아과 - 총론</c:when>
			    <c:when test="${p eq 'P1'}">소아과 - 감염, 골격계, 내분비, 면역학, 비뇨기, 소화기</c:when>
			    <c:when test="${p eq 'P2'}">소아과 - 순환기, 신경계, 알레르기, 종양, 혈액, 호흡기</c:when>
			    <c:when test="${p eq 'IMG'}">소화기내과</c:when>
			    <c:when test="${p eq 'IMC'}">순환기내과</c:when>
			    <c:when test="${p eq 'IMN'}">신장내과</c:when>
			    <c:when test="${p eq 'IMA'}">알레르기내과</c:when>
			    <c:when test="${p eq 'GS1'}">외과 - 간담췌, 내분비, 유방, 소화기</c:when>
			    <c:when test="${p eq 'GS2'}">외과 - 소아, 총론, 혈관</c:when>
			    <c:when test="${p eq 'PSY'}">정신과</c:when>
			    <c:when test="${p eq 'IMO'}">종양내과</c:when>
			    <c:when test="${p eq 'IMH'}">혈액내과</c:when>
			    <c:when test="${p eq 'IMP'}">호흡기내과</c:when>
			    <c:otherwise>알 수 없음</c:otherwise>
			</c:choose>
            <c:if test="${!status.last}">, </c:if>
        </c:forEach>
        분야, 
        <!-- 년도는 그대로 출력 -->
        <c:forEach var="y" items="${fn:split(param.year, ',')}" varStatus="status">
            ${y}<c:if test="${!status.last}">, </c:if>
        </c:forEach>
        년도 기출문제 입니다.
    </p>
    
    <!-- 문제 개수와 제한시간 출력 -->
    <p>
        총 <c:out value="${fn:length(problemIdList)}"/> 문제이고, 제한시간은 
        <c:out value="${fn:length(problemIdList) * 78}"/> 초 입니다.
    </p>
    
	<form id="trainingForm" action="/ex01/problem/training" method="GET">
	    <!-- 선택한 problemIdList를 hidden input으로 전달 -->
	    <c:forEach var="problemId" items="${problemIdList}">
	        <input type="hidden" name="problemIds" value="${problemId}">
	    </c:forEach>
	    
	    <button type="submit">문제 풀기</button>
	    
	    <div>
			<!-- 스프링 시큐리티를 사용하면 모든 post 전송에 csrf 토큰을 추가해야 함 -->
			<input type="hidden" name="${_csrf.parameterName }"
				value="${_csrf.token }">
		</div>		
	</form>
    
    
</body>
</html>