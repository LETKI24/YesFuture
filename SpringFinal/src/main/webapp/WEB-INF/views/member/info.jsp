<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.min.js">
</script>
<title>회원 정보</title>
</head>
<body>
	<header>
    	<a href="../board/list">메인</a>
  	</header>
	<p>아이디 : ${memberDTO.memberId }</p>
	<p>회원 이름 : ${memberDTO.memberName }</p>
	<p>회원 등록일 : ${memberDTO.regDate }</p>

	<button id="modifyMember">정보 수정</button>
	<button id="deleteMember">회원 탈퇴</button>		

	<form id="deleteForm" action="delete" method="POST">
    	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
	</form>
	
	<script type="text/javascript">
	$(document).ready(function(){
		
		$("#modifyMember").click(function(){
			window.location.href = 'modify'; // /member/modify url로 이동
		}); // end click()
		
		$('#deleteMember').click(function(){
			if(confirm('탈퇴하시겠습니까?')) {
				var deleteForm = $("#deleteForm"); // form 객체 참조
				deleteForm.submit(); // form 전송
			}
		}); // end click()
	});

	</script>
</body>
</html>