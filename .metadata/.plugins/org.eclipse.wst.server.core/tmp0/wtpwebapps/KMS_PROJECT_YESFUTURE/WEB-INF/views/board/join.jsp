<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<!-- jquery 라이브러리 import -->
<script src="https://code.jquery.com/jquery-3.7.1.js">
	
</script>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<style>
/* 경고 메시지를 빨간색으로 표시 */
.error-message {
	color: red;
	font-size: 20px;
	display: none; /* 기본적으로 숨기기 */
}
</style>

<meta charset="UTF-8">
<title>YesFuture 회원가입</title>
</head>
<body>
	포르쉐 사자

	<h2>회원 가입</h2>
	<form action="join" method="post" onsubmit="return checkPassword() && combineEmail()">
		<label for="memberName">회원 이름 :</label> <input type="text"
			id="memberName" name="memberName" required><br> <br>

		<label for="memberNickname">회원 닉네임 :</label> <input type="text"
			id="memberNickname" name="memberNickname" required><br>
		<!-- 중복 닉네임 경고 메시지 -->
		<span id="nicknameError" style="color: red; display: none;">중복된
			닉네임 입니다.</span> <br> 
			
			<label for="memberEmailId">회원 이메일 :</label> <input
			type="text" id="memberEmailId" name="memberEmailId" required> @ <select
			id="emailSelect" name="emailSelect" required>
			<option value="naver.com">naver.com</option>
			<option value="gmail.com">gmail.com</option>
			<option value="yahoo.com">yahoo.com</option>
		</select><br>
		
		<input type="hidden" id="memberEmail" name="memberEmail">

		<!-- 중복 이메일 경고 메시지 -->
		<span id="emailError" style="color: red; display: none;">이미
			가입된 이메일 입니다.</span> <br> 
			<label for="memberPw">회원 비밀번호 :</label> 
			<input type="password" id="memberPw" name="memberPw" required>
		<!-- 눈 모양 아이콘 (FontAwesome 아이콘 예시) -->
		<i id="togglePassword" class="fa fa-eye" style="cursor: pointer;"></i><br>

		<!-- 비밀번호 조건 불충족 메시지를 표시할 부분 -->
		<span id="passwordConditionError" class="error-message">비밀번호는
			8~16자 영문 대소문자, 숫자, !@#$%^&*와 같은 특수문자를 포함해주세요.</span> <br> <br> 
			<label for="confirmPassword">회원 비밀번호 확인 :</label> 
			<input type="password" id="confirmPassword" name="confirmPassword" required><br>
		<br> 
		
		<label for="memberSchool">회원 출신 학교 :</label> 
		<select id="memberSchool" name="memberSchool" required>
			<option value="연세대학교 의과대학">연세대학교 의과대학</option>
			<option value="서울대학교 의과대학">서울대학교 의과대학</option>
		</select><br> <br> 
		
		<label for="memberPhone">회원 전화번호 :</label>
		<textarea id="memberPhone" name="memberPhone" placeholder="-없이 입력"
			required></textarea>
		<br> <br> 
		
		<input type="submit" id="submitButton" value="회원 가입" disabled>
		
		<!-- 스프링 시큐리티를 사용하면 모든 post 전송에 csrf 토큰을 추가해야 함 -->
      	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
	</form>

	<script type="text/javascript">
		// ajaxSend() : AJAX 요청이 전송되려고 할 때 실행할 함수를 지정
		// ajax 요청을 보낼 때마다 CSRF 토큰을 요청 헤더에 추가하는 코드
		$(document).ajaxSend(function(e, xhr, opt){
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			
			xhr.setRequestHeader(header, token);
		});
		
	
		$(document).ready(function() {
			
			// 이메일 주소 합치기
			function combineEmail() {
				// 이메일 조합
		        const emailLocal = document.getElementById("memberEmailId").value;
		        const emailDomain = document.getElementById("emailSelect").value;
		        const memberEmail = emailLocal + "@" + emailDomain;
		        
		        console.log("email : "+ memberEmail)
		        
		        // 조합된 이메일을 hidden input에 설정
		        document.getElementById("memberEmail").value = memberEmail;
		        
		        checkEmailDuplicate(memberEmail);
			}			
			
			let nicknameValid = false;
		    let emailValid = false;
		    let passwordValid = false;
						
			// 닉네임 중복확인 기능
			$('#memberNickname').focusout(function() {
				var memberNickname = $('#memberNickname').val(); // 입력된 닉네임 가져오기

				// $.ajax로 송수신
				if (memberNickname != null && memberNickname != "") {
					$.ajax({
						type : 'POST', // 메서드 타입
						url : '../board/nickname', // url
						//	headers : { // 헤더 정보
						//		'Content-Type' : 'application/json' // json content-type 설정
						//	}, // 여기 'application/json'이 있어야 문제가 안생긴다.
						data : {
							memberNickname : memberNickname
						}, // 파라미터로 전송
						success : function(result) { // 전송 성공 시 서버에서 result 값 전송
							console.log(result);
							if (result == 1) {
								// 중복 닉네임이 있으면 경고 메시지 보이기
								$('#nicknameError').show();
								nicknameValid = false;
							} else {
								// 중복이 없으면 경고 메시지 숨기기
								$('#nicknameError').hide();
								nicknameValid = true;
							}
							validateForm();
						}
					});
				}
			})// end focusout - nickname duplicate

			// 이메일 중복확인 기능
			function checkEmailDuplicate(memberEmail) {
				var memberEmailId = $('#memberEmailId').val(); // 입력된 이메일 가져오기

				// $.ajax로 송수신
				if (memberEmailId != null && memberEmailId != "") {
					$.ajax({
						type : 'POST', // 메서드 타입
						url : '../board/email', // url
						data : {
							memberEmail : memberEmail
						}, // 파라미터로 전송
						success : function(result) { // 전송 성공 시 서버에서 result 값 전송
							console.log(result);
							if (result == 1) {
								// 중복 이메일 있으면 경고 메시지 보이기
								$('#emailError').show();
								emailValid = false;
							} else {
								// 중복이 없으면 경고 메시지 숨기기
								$('#emailError').hide();
								emailValid = true;
							}
							validateForm();
						}
					});
				}
			} // end focusout - email duplicate
			
		    $('#memberEmailId, #emailSelect').on('focusout', function() {
		        combineEmail(); // 이메일 합치기 + 중복 검사 실행
		    });			
			
			// 비밀번호 확인 체크
			function checkPassword() {
				var memberPw = document.getElementById("memberPw").value;
				var confirmPassword = document.getElementById("confirmPassword").value;
				var errorMessage = document
						.getElementById("passwordConditionError");

				// 비밀번호 조건 (8~16자, 영문 대/소문자, 숫자, 특수문자)
				const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[a-zA-Z\d!@#$%^&*]{8,16}$/;

				if (!passwordRegex.test(memberPw)) {
					errorMessage.style.display = 'inline'; // 경고 메시지 보이기
					passwordValid = false;
				} else if (memberPw !== confirmPassword) {
					// 비밀번호가 조건을 만족하지 않으면 경고 메시지 표시
					errorMessage.innerText = '비밀번호가 일치하지 않습니다.';
					errorMessage.style.display = 'inline'; // 경고 메시지 보이기
					passwordValid = false;
				} else {
					errorMessage.style.display = 'none'; // 경고 메시지 숨기기
					passwordValid = true;
				}
				validateForm();
			}

			// 비밀번호 확인 입력 필드에서 포커스를 잃을 때마다 checkPassword() 호출
			document.getElementById("memberPw").addEventListener("focusout",
					checkPassword);
			document.getElementById("confirmPassword").addEventListener("focusout",
					checkPassword);

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
			
			// 폼 전체 검증 함수
		    function validateForm() {
		        if (nicknameValid && emailValid && passwordValid) {
		            $('#submitButton').prop('disabled', false); // 버튼 활성화
		        } else {
		            $('#submitButton').prop('disabled', true); // 버튼 비활성화
		        }
		    }
						
		}); // end document
		

	</script>

</body>
</html>

