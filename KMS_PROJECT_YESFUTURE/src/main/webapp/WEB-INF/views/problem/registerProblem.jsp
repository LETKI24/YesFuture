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
	
<title>관리자-문제 등록하기</title>
</head>
<body>
	<h2>문제 등록하기</h2>
	<form id="registerProblemForm" action="registerProblem" method="POST">
		<!-- input 태그의 name은 vo의 멤버 변수 이름과 동일하게 작성 -->
		<div>
			<input type="hidden" name="memberNickname"
				value="<sec:authentication property="principal.member.memberNickname"/>"
				>
			<input type="hidden" name="memberId"
				value="<sec:authentication property="principal.member.memberId"/>"
				>
		</div>
		<div>
			<p>문제 분과 :</p>
			<input type="text" id="problemPart" 
				name="problemPart" placeholder="분과 입력, ex)산과=OB"
				maxlength="10" required>
		</div>
		<div>
			<p>문제 진단 :</p>
			<input type="text" id="problemDiagnosis" 
				name="problemDiagnosis" placeholder="영어로 입력"
				maxlength="100" required>
		</div>
		<div>
			<p>문제 출제년도 :</p>
			<input type="text" id="problemYear" 
				name="problemYear" placeholder="숫자만 입력 ex)2025"
				maxlength="100" required>
		</div>
		<div>
			<p>문제 지문 :</p>
			<textarea rows="20" cols="120" id="problemContent"
				name="problemContent" placeholder="내용 입력" maxlength="2000"
				required></textarea>
		</div>
		<div>
			<p>문제 보기 :</p>
			1) ※※ 1번보기엔 정답을 입력해야합니다. ※※
			<br>
			<input type="text" id="optionCorrect" 
				name="optionContent" placeholder="정답만 입력"
				maxlength="300" required>
			<br>
			
			2) 
			<br>
			<input type="text" id="option2nd" 
				name="optionContent" placeholder="2번 보기"
				maxlength="300" required>
			<br>
			
			3)
			<br>
			<input type="text" id="option3rd" 
				name="optionContent" placeholder="3번 보기"
				maxlength="300" required>
			<br>
			
			4)
			<br>
			<input type="text" id="option4th" 
				name="optionContent" placeholder="4번 보기"
				maxlength="300" required>
			<br>
			
			5)
			<br>
			<input type="text" id="option5th" 
				name="optionContent" placeholder="5번 보기"
				maxlength="300" required>
			<br>
				
			<!-- 결과 문자열을 담을 hidden 필드 -->
        	<input type="hidden" id="optionContentAll" name="optionContentAll">
					
		</div>	
		<div>
			<!-- 스프링 시큐리티를 사용하면 모든 post 전송에 csrf 토큰을 추가해야 함 -->
			<input type="hidden" name="${_csrf.parameterName }"
				value="${_csrf.token }">
		</div>		
	</form>
	
	<div class="image-upload">
		<h2>이미지 파일 업로드</h2>
		<p>* 이미지 파일은 최대 3개까지 가능합니다.</p>
		<p>* 최대 용량은 10MB 입니다.</p>
		<div class="image-drop"></div>
		<h2>선택한 이미지 파일 :</h2>
		<div class="image-list"></div>
	</div>
	
	<div class="attachProblemVOImg-list"></div>
	
	<button id="registerProblem">문제 등록</button>
	
	<script src="${pageContext.request.contextPath }/resources/js/imageProblem.js"></script>
	
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
			$('#registerProblem').click(function() {
				var part = $('#problemPart').val().trim(); // 문자열의 양 끝 공백 제거
	            var content = $('#problemContent').val().trim();
				var diagnosis = $('#problemDiagnosis').val().trim();
				var year = $('#problemYear').val().trim();
				var optionCorrect = $('#optionCorrect').val().trim();
				var option2nd = $('#option2nd').val().trim();
				var option3rd = $('#option3rd').val().trim();
				var option4th = $('#option4th').val().trim();
				var option5th = $('#option5th').val().trim();
				
				
	            if (part === '' || content === '' || diagnosis === '' || year === '' || optionCorrect === '' 
	            		|| option2nd === '' || option3rd === '' || option4th === '' || option5th === '') {
	                alert("모두 입력해주세요.");
	                return;
	            }
	            
	            // ' | ' 구분자로 이어붙이기
	            var allOptions = optionCorrect + "|" + option2nd + "|" 
	            				+ option3rd + "|" + option4th + "|" + option5th;
	            	            
	            // hidden 필드에 저장 (name="optionContentAll"로 컨트롤러에서 구분)
	            $("#optionContentAll").val(allOptions);
	            
				// form 객체 참조
				var registerForm = $('#registerProblemForm');
				
				// attachDTOImg-list의 각 input 태그 접근
				var i = 0;
				$('.attachProblemVOImg-list input[name="attachProblemVO"]').each(function(){
					console.log(this);
					// JSON attachProblemVO 데이터를 object 변경
					var attachProblemVO = JSON.parse($(this).val());
					// attachQuestionPath input 생성
					var inputPath = $('<input>').attr('type', 'hidden')
							.attr('name', 'attachProblemList[' + i + '].attachProblemPath');
					inputPath.val(attachProblemVO.attachProblemPath);
					
					// attachRealName input 생성
					var inputRealName = $('<input>').attr('type', 'hidden')
							.attr('name', 'attachProblemList[' + i + '].attachProblemRealName');
					inputRealName.val(attachProblemVO.attachProblemRealName);
					
					// attachChgName input 생성
					var inputChgName = $('<input>').attr('type', 'hidden')
							.attr('name', 'attachProblemList[' + i + '].attachProblemChgName');
					inputChgName.val(attachProblemVO.attachProblemChgName);
					
					// attachExtension input 생성
					var inputExtension = $('<input>').attr('type', 'hidden')
							.attr('name', 'attachProblemList[' + i + '].attachProblemExtension');
					inputExtension.val(attachProblemVO.attachProblemExtension);
					
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