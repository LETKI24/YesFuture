<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<!-- css 파일 불러오기 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/image.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/attach.css">

<title>질문글 작성 페이지</title>
</head>
<body>
	<h2>질문글 쓰기</h2>
	<form id="registerQuestionForm" action="registerQuestion" method="POST">
		<!-- input 태그의 name은 vo의 멤버 변수 이름과 동일하게 작성 -->
		<div>
			<p>작성자 :</p>
			<input type="text" name="memberNickname"
				value="<sec:authentication property="principal.member.memberNickname"/>"
				readonly>
		</div>
		<div>
			<p>제목 :</p>
			<input type="text" id="boardQuestionTitle" 
				name="boardQuestionTitle" placeholder="제목 입력"
				maxlength="45" required>(한글 45자 이내)
		</div>

		<div>
			<p>내용 :</p>
			<textarea rows="20" cols="120" id="boardQuestionContent"
				name="boardQuestionContent" placeholder="내용 입력" maxlength="300"
				required></textarea>
		</div>
		<div>

			<!-- 스프링 시큐리티를 사용하면 모든 post 전송에 csrf 토큰을 추가해야 함 -->
			<input type="hidden" name="${_csrf.parameterName }"
				value="${_csrf.token }">
		</div>
	</form>

	<hr>

	<div class="image-upload">
		<h2>이미지 파일 업로드</h2>
		<p>* 이미지 파일은 최대 3개까지 가능합니다.</p>
		<p>* 최대 용량은 10MB 입니다.</p>
		<div class="image-drop"></div>
		<h2>선택한 이미지 파일 :</h2>
		<div class="image-list"></div>
	</div>

	<hr>
	
	<div class="attach-upload">
		<h2>첨부 파일 업로드</h2>
		<p>* 첨부 파일은 최대 3개까지 가능합니다.</p>
		<p>* 최대 용량은 10MB 입니다.</p>
		<input type="file" id="attachInput" name="files" multiple="multiple"><br>
		<h2>선택한 첨부 파일 정보 :</h2>
		<div class="attach-list"></div>
	</div>

	<div class="attachQuestionVOImg-list"></div>

	<div class="attachQuestionVOFile-list"></div>

	<button id="registerBoard">질문글 등록</button>

	<script src="${pageContext.request.contextPath }/resources/js/image.js"></script>
	<script	src="${pageContext.request.contextPath }/resources/js/attach.js"></script>

	<script>
		// ajaxSend() : AJAX 요청이 전송되려고 할 때 실행할 함수를 지정
		// ajax 요청을 보낼 때마다 CSRF 토큰을 요청 헤더에 추가하는 코드
		$(document).ajaxSend(function(e, xhr, opt) {
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");

			xhr.setRequestHeader(header, token);
		});

		$(document).ready(function() {
			// regsiterForm 데이터 전송
			$('#registerBoard').click(function() {
				var title = $('#boardQuestionTitle').val().trim(); // 문자열의 양 끝 공백 제거
	            var content = $('#boardQuestionContent').val().trim();

	            if (title === '' || content === '') {
	                alert("제목과 내용을 모두 입력해주세요.");
	                return;
	            }
	            
				// form 객체 참조
				var registerForm = $('#registerQuestionForm');
				
				// attachDTOImg-list의 각 input 태그 접근
				var i = 0;
				$('.attachQuestionVOImg-list input[name="attachQuestionVO"]').each(function(){
					console.log(this);
					// JSON attachQuestionDTO 데이터를 object 변경
					var attachQuestionVO = JSON.parse($(this).val());
					// attachQuestionPath input 생성
					var inputPath = $('<input>').attr('type', 'hidden')
							.attr('name', 'attachQuestionList[' + i + '].attachQuestionPath');
					inputPath.val(attachQuestionVO.attachQuestionPath);
					
					// attachRealName input 생성
					var inputRealName = $('<input>').attr('type', 'hidden')
							.attr('name', 'attachQuestionList[' + i + '].attachQuestionRealName');
					inputRealName.val(attachQuestionVO.attachQuestionRealName);
					
					// attachChgName input 생성
					var inputChgName = $('<input>').attr('type', 'hidden')
							.attr('name', 'attachQuestionList[' + i + '].attachQuestionChgName');
					inputChgName.val(attachQuestionVO.attachQuestionChgName);
					
					// attachExtension input 생성
					var inputExtension = $('<input>').attr('type', 'hidden')
							.attr('name', 'attachQuestionList[' + i + '].attachQuestionExtension');
					inputExtension.val(attachQuestionVO.attachQuestionExtension);
					
					// form에 태그 추가
					registerForm.append(inputPath);
					registerForm.append(inputRealName);
					registerForm.append(inputChgName);
					registerForm.append(inputExtension);
					
					i++;
				});
				
				// attachVOFile-list의 각 input 태그 접근
				$('.attachQuestionVOFile-list input[name="attachQuestionVO"]').each(function(){
					console.log(this);
					// JSON attachQuestionVO 데이터를 object 변경
					var attachQuestionVO = JSON.parse($(this).val());
					// attachPath input 생성
					var inputPath = $('<input>').attr('type', 'hidden')
							.attr('name', 'attachQuestionList[' + i + '].attachQuestionPath');
					inputPath.val(attachQuestionVO.attachQuestionPath);
					
					// attachRealName input 생성
					var inputRealName = $('<input>').attr('type', 'hidden')
							.attr('name', 'attachQuestionList[' + i + '].attachQuestionRealName');
					inputRealName.val(attachQuestionVO.attachQuestionRealName);
					
					// attachChgName input 생성
					var inputChgName = $('<input>').attr('type', 'hidden')
							.attr('name', 'attachQuestionList[' + i + '].attachQuestionChgName');
					inputChgName.val(attachQuestionVO.attachQuestionChgName);
					
					// attachExtension input 생성
					var inputExtension = $('<input>').attr('type', 'hidden')
							.attr('name', 'attachQuestionList[' + i + '].attachQuestionExtension');
					inputExtension.val(attachQuestionVO.attachQuestionExtension);
					
					// form에 태그 추가
					registerForm.append(inputPath);
					registerForm.append(inputRealName);
					registerForm.append(inputChgName);
					registerForm.append(inputExtension);
					
					i++;
				});
				registerForm.submit();
			});

		});
	</script>


</body>
</html>