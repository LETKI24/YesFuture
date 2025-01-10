<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>

<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<meta charset="UTF-8">
<title>${boardQuestionVO.boardQuestionTitle }</title>
<!-- css 파일 불러오기 -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/image.css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/attach.css">
<!-- jquery 라이브러리 import -->
<script src="https://code.jquery.com/jquery-3.7.1.js">
</script>

</head>
<body>
	<h2>질문글 수정</h2>
	<form id="modifyForm" action="modifyQuestion" method="POST">
		<div>
			<input type="hidden" name="boardQuestionId" value="${boardQuestionVO.boardQuestionId }">
		</div>
		<div>
			<p>제목 : </p>
			<input type="text" name="boardQuestionTitle" placeholder="제목 입력" 
			maxlength="45" value="${boardQuestionVO.boardQuestionTitle }" required>(한글 45자 이내)
		</div>
		<div>
			<p>작성자 : ${boardQuestionVO.memberNickname} </p>
			
		</div>
		<div>
			<p>내용 : </p>
			<textarea rows="20" cols="120" name="boardQuestionContent" placeholder="내용 입력" 
			maxlength="300" required>${boardQuestionVO.boardQuestionContent }</textarea>
		</div>
		
		<!-- 기존 첨부 파일 리스트 데이터 구성 -->
		<c:forEach var="attachQuestionVO" items="${boardQuestionVO.attachQuestionList}" varStatus="status">
			<input type="hidden" class="input-attach-list" name="attachQuestionList[${status.index }]
				.attachQuestionPath" value="${attachQuestionVO.attachQuestionPath }">
			<input type="hidden" class="input-attach-list" name="attachQuestionList[${status.index }]
				.attachQuestionRealName" value="${attachQuestionVO.attachQuestionRealName }">
			<input type="hidden" class="input-attach-list" name="attachQuestionList[${status.index }]
				.attachQuestionChgName" value="${attachQuestionVO.attachQuestionChgName }">
			<input type="hidden" class="input-attach-list" name="attachQuestionList[${status.index }]
				.attachQuestionExtension" value="${attachQuestionVO.attachQuestionExtension }">
		</c:forEach>
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
	</form>
	
	<hr>
	
	<button id="change-upload">첨부 파일 변경</button>
	
	<!-- 이미지 파일 영역 -->
	<div class="image-upload">
		<div class="image-view">
			<h2>이미지 파일 리스트</h2>
			<div class="image-list">
				<!-- 이미지 파일 처리 코드 -->
				<c:forEach var="attachQuestionVO" items="${boardQuestionVO.attachQuestionList}">
				    <c:if test="${attachQuestionVO.attachQuestionExtension eq 'jpg' or 
				    			  attachQuestionVO.attachQuestionExtension eq 'jpeg' or 
				    			  attachQuestionVO.attachQuestionExtension eq 'png' or 
				    			  attachQuestionVO.attachQuestionExtension eq 'gif'}">
				        <div class="image_item">
				        	<a href="../../image/get?attachQuestionId=${attachQuestionVO.attachQuestionId }"
				        		 target="_blank">
				        	<!-- 아래 img는 썸네일 관련 코드 -->
					        <img width="100px" height="100px" 
					        src="../../image/get?attachQuestionId=${attachQuestionVO.attachQuestionId }
					        	&attachQuestionExtension=${attachQuestionVO.attachQuestionExtension}"/>
					        </a>
				        </div>
				    </c:if>
				</c:forEach>
			</div>
		</div>
		<div class="image-modify" style="display : none;">
			<h2>이미지 파일 업로드</h2>
			<p>* 이미지 파일은 최대 3개까지 가능합니다.</p>
			<p>* 최대 용량은 10MB 입니다.</p>
			<div class="image-drop"></div>
			<h2>선택한 이미지 파일 :</h2>
			<div class="image-list"></div>
		</div>
	</div>
	
	<!-- 첨부 파일 영역 -->
	<div class="attach-upload">
		<div class="attach-view">
			<h2>첨부 파일 리스트</h2>
			<div class="attach-list">
			<c:forEach var="attachQuestionVO" items="${boardQuestionVO.attachQuestionList}">
		 		<c:if test="${not (attachQuestionVO.attachQuestionExtension eq 'jpg' or 
			    			  attachQuestionVO.attachQuestionExtension eq 'jpeg' or 
			    			  attachQuestionVO.attachQuestionExtension eq 'png' or 
			    			  attachQuestionVO.attachQuestionExtension eq 'gif')}">
			    	<div class="attach_item">
			    	<p><a href="../../attach/download?attachQuestionId=${attachQuestionVO.attachQuestionId }">
			    		${attachQuestionVO.attachQuestionRealName }.${attachQuestionVO.attachQuestionExtension }
			    		</a></p>
			    	</div>
			    </c:if>
			</c:forEach>		
			</div>
		</div>
		<div class="attach-modify" style="display : none;">
			<h2>첨부 파일 업로드</h2>
			<p>* 첨부 파일은 최대 3개까지 가능합니다.</p>
			<p>* 최대 용량은 10MB 입니다.</p>
			<input type="file" id="attachInput" name="files" multiple="multiple"><br>
			<h2>선택한 첨부 파일 정보 :</h2>
			<div class="attach-list"></div>
		</div>
	</div>
	
	<div class="attachQuestionVOImg-list">
	</div>
	
	<div class="attachQuestionVOFile-list">
	</div>

	<button id="modifyBoard">등록</button>

	<script src="${pageContext.request.contextPath }/resources/js/image.js"></script>
	<script	src="${pageContext.request.contextPath }/resources/js/attach.js"></script>
	
	<script type="text/javascript">

	$(document).ajaxSend(function(e, xhr, opt){
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
		xhr.setRequestHeader(header, token);
	});

	$(document).ready(function(){
	    // 이미지 변경 버튼 클릭 시
	    $('#change-upload').click(function(){
	    	if(!confirm('기존에 업로드 파일들은 삭제됩니다. 계속 하시겠습니까?')){
	    		return;
	    	}
	        $('.image-modify').show();
	        $('.image-view').hide();
	        $('.attach-modify').show();
	        $('.attach-view').hide();
	        $('.input-attach-list').remove(); // input-attach-list 삭제
	    });

		// modifyForm 데이터 전송
		$('#modifyBoard').click(function() {
			// form 객체 참조
			var modifyForm = $('#modifyForm');
			
			// attachQuestionVOImg-list의 각 input 태그 접근
			var i = 0;
			$('.attachQuestionVOImg-list input[name="attachQuestionVO"]').each(function(){
				console.log(this);
				// JSON attachQuestionVO 데이터를 object 변경
				var attachQuestionVO = JSON.parse($(this).val());
				
				// attachQuestionPath input 생성
				var inputPath = $('<input>').attr('type', 'hidden')
						.attr('name', 'attachQuestionList[' + i + '].attachQuestionPath');
				inputPath.val(attachQuestionVO.attachQuestionPath);
				
				// attachQuestionRealName input 생성
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
				modifyForm.append(inputPath);
				modifyForm.append(inputRealName);
				modifyForm.append(inputChgName);
				modifyForm.append(inputExtension);
				
				i++;
			});
			
			// attachQuestionVOFile-list의 각 input 태그 접근
			$('.attachQuestionVOFile-list input[name="attachQuestionVO"]').each(function(){
				console.log(this);
				// JSON attachQuestionVO 데이터를 object 변경
				var attachQuestionVO = JSON.parse($(this).val());
				
				// attachQuestionPath input 생성
				var inputPath = $('<input>').attr('type', 'hidden')
						.attr('name', 'attachQuestionList[' + i + '].attachQuestionPath');
				inputPath.val(attachQuestionVO.attachQuestionPath);
				
				// attachQuestionRealName input 생성
				var inputRealName = $('<input>').attr('type', 'hidden')
						.attr('name', 'attachQuestionList[' + i + '].attachQuestionRealName');
				inputRealName.val(attachQuestionVO.attachQuestionRealName);
				
				// attachQuestionChgName input 생성
				var inputChgName = $('<input>').attr('type', 'hidden')
						.attr('name', 'attachQuestionList[' + i + '].attachQuestionChgName');
				inputChgName.val(attachQuestionVO.attachQuestionChgName);
				
				// attachQuestionExtension input 생성
				var inputExtension = $('<input>').attr('type', 'hidden')
						.attr('name', 'attachQuestionList[' + i + '].attachQuestionExtension');
				inputExtension.val(attachQuestionVO.attachQuestionExtension);
				
				// form에 태그 추가
				modifyForm.append(inputPath);
				modifyForm.append(inputRealName);
				modifyForm.append(inputChgName);
				modifyForm.append(inputExtension);
				
				i++;
			});
			modifyForm.submit();
		});
	});
	</script>
	
	
</body>
</html>