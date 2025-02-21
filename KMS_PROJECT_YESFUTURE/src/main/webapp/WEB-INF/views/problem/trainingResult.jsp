<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채점 결과</title>
</head>
<body>

    <!-- 맞춘 문제와 틀린 문제 카운트 초기화 -->
    <c:set var="correctCount" value="0" />
    <c:set var="incorrectCount" value="0" />
    
    <!-- 리스트 순회하면서 scoring에 따른 카운트 증가 -->
    <c:forEach var="record" items="${trainingRecordList}">
        <c:if test="${record.scoring == 1}">
            <c:set var="correctCount" value="${correctCount + 1}" />
        </c:if>
        <c:if test="${record.scoring == 0}">
            <c:set var="incorrectCount" value="${incorrectCount + 1}" />
        </c:if>
    </c:forEach>
    
    <c:set var="accuracy" value="${correctCount / (correctCount + incorrectCount)}" />
    
    <!-- 출력문 -->
    <p>
        맞춘 문제는 <c:out value="${correctCount}" /> 문제이고 
        틀린 문제는 <c:out value="${incorrectCount}" /> 문제입니다.
        정답율은 <fmt:formatNumber value="${accuracy}" pattern="0.00" /> 입니다.
    </p>
    <p>고생하셨습니다.</p>
    
</body>
</html>