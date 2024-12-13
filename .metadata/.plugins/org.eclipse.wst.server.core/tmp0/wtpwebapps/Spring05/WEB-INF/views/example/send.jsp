<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="send" method="POST">
		<input type="text" name="title"><br>
		<textarea rows="2" cols="50" name="content"></textarea><br>
		<!-- CSRF 토큰 : POST방식의 form태그에 히든 방식으로 반드시 넣어줘야함, 안그럼 데이터 안넘어감 -->
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
		<input type="submit" value="전송">
	</form>

</body>
</html>