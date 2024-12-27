<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
	<h2>로그인</h2>
	<form action="login" method="post">
		<label for="memberEmail">회원 이메일 :</label> 
		<input type="text" id="memberEmail" name="memberEmail" required> <br>
		
		<br> <label for="memberPw">회원 비밀번호 :</label> 
		<input type="password" id="memberPw" name="memberPw" required>
		<!-- 눈 모양 아이콘 (FontAwesome 아이콘 예시) -->
		<i id="togglePassword" class="fa fa-eye" style="cursor: pointer;"></i><br>
				
		<br> <input type="submit" id="loginButton" value="로그인">
	</form>

	<script type="text/javascript">
		// 비밀번호 보이기/숨기기 토글 기능
		const togglePassword = document.getElementById('togglePassword');
		const passwordField = document.getElementById('memberPw');

		togglePassword.addEventListener('click', function() {
			// 비밀번호 필드의 type을 'password'와 'text'로 토글
			const type = passwordField.type === 'password' ? 'text'
					: 'password';
			passwordField.type = type;

			// 아이콘의 변경 (보이기/숨기기 아이콘을 바꿈)
			this.classList.toggle('fa-eye'); // 눈 모양 아이콘
			this.classList.toggle('fa-eye-slash'); // 눈 감은 아이콘
		});
		
		window.onload = function() {
			var loginError = "${loginError}";
			if (loginError) {
				alert(loginError); // 로그인 실패 시 alert 메시지
			}
		};
	</script>
</body>
</html>