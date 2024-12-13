<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>데이터 전송 실습</title>
</head>
<body>
   <!-- action="send" -> example2/send 위치라는 의미(상대경로) -->
   <form action="send" method="POST">
      <input type="text" name="name"><br>
      <input type="text" name="age"><br>
      <input type="submit" value="전송">
   </form>
</body>
</html>
