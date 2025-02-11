<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<script>
	    // JavaScript에서 사용할 JSON 배열 생성
	    var problemList = [
	        <c:forEach var="problem" items="${problemList}" varStatus="status">
	            {
	                "id": "${problem.problemId}",
	                "content": "${problem.problemContent}",
	                "diagnosis": "${problem.problemDiagnosis}",
	                "year": "${problem.problemYear}",
	                "part": "${problem.problemPart}",
	                "source": "${problem.problemSource}",		
	                "memberId": "${problem.memberId}",
	                "attach": "${problem.attachProblemList}",
	                "option": "${problem.optionList}"
	            }<c:if test="${!status.last}">,</c:if>
	        </c:forEach>
	    ];
	
	    // JSON 데이터 확인 (브라우저 콘솔에서 확인 가능)
	    console.log(problemList);
	</script>

</body>
</html>