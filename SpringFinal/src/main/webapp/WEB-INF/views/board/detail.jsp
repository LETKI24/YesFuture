<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<!-- css 파일 불러오기 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/image.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/attach.css">
	
<style type="text/css">
li {
	display: inline-block;
}
</style>

<script src="https://code.jquery.com/jquery-3.7.1.min.js">
</script>
<meta charset="UTF-8">
<spring:htmlEscape defaultHtmlEscape="true" />
<title>${boardDTO.boardTitle }</title>
</head>
<body>

	<%@ include file="../common/header.jsp" %>
	<h2>글 보기 페이지</h2>
	<div>
		<p>글 번호 : ${boardDTO.boardId }</p>
	</div>
	<div>
		<p>제목 : </p>
		<p><c:out value="${boardDTO.boardTitle }" /></p>
	</div>
	<div>
		<p>작성자 : ${boardDTO.memberId }</p>
		<p>작성일 : ${boardDTO.boardDateCreated }</p>
	</div>
	<div>
		<textarea rows="20" cols="120" readonly>${boardDTO.boardContent }</textarea>
	</div>

	
	<button id="listBoard">글 목록</button>
	<sec:authentication property="principal" var="user"/>	
	<sec:authorize access="isAuthenticated()">
		<c:if test="${boardDTO.memberId eq user.username}">
			<button id="modifyBoard">글 수정</button>
			<button id="deleteBoard">글 삭제</button>		
		</c:if>
	</sec:authorize>
	
	<form id="listForm" action="list" method="GET">
		<input type="hidden" name="pageNum" >
    	<input type="hidden" name="pageSize" >
    	<input type="hidden" name="type" >
    	<input type="hidden" name="keyword" >	
	</form>
	<form id="modifyForm" action="modify" method="GET">
		<input type="hidden" name="boardId">
		<input type="hidden" name="pageNum" >
    	<input type="hidden" name="pageSize" >
    	<input type="hidden" name="type" >
    	<input type="hidden" name="keyword" >
	</form>
	<form id="deleteForm" action="delete" method="POST">
		<input type="hidden" name="boardId">
		<input type="hidden" name="pageNum" >
    	<input type="hidden" name="pageSize" >
    	<input type="hidden" name="type" >
    	<input type="hidden" name="keyword" >
    	<input type="hidden" name="memberId" value="${boardDTO.memberId}">
    	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
	</form>
	
	<!-- 이미지 파일 영역 -->
	<div class="image-upload">
		<div class="image-view">
			<h2>이미지 파일 리스트</h2>
			<div class="image-list">
				<!-- 이미지 파일 처리 코드 -->
				<c:forEach var="attachDTO" items="${boardDTO.attachList}">
				    <c:if test="${attachDTO.attachExtension eq 'jpg' or 
				    			  attachDTO.attachExtension eq 'jpeg' or 
				    			  attachDTO.attachExtension eq 'png' or 
				    			  attachDTO.attachExtension eq 'gif'}">
				        <div class="image_item">
				        	<a href="../image/get?attachId=${attachDTO.attachId }" target="_blank">
					        <img width="100px" height="100px" 
					        src="../image/get?attachId=${attachDTO.attachId }&attachExtension=${attachDTO.attachExtension}"/></a>
				        </div>
				    </c:if>
				</c:forEach>
			</div>
		</div>
	</div>
	
	<!-- 첨부 파일 영역 -->
	<div class="attach-upload">
		<div class="attach-view">
			<h2>첨부 파일 리스트</h2>
			<div class="attach-list">
			<c:forEach var="attachDTO" items="${boardDTO.attachList}">
		 		<c:if test="${not (attachDTO.attachExtension eq 'jpg' or 
			    			  attachDTO.attachExtension eq 'jpeg' or 
			    			  attachDTO.attachExtension eq 'png' or 
			    			  attachDTO.attachExtension eq 'gif')}">
			    	<div class="attach_item">
			    	<p><a href="../attach/download?attachId=${attachDTO.attachId }">${attachDTO.attachRealName }.${attachDTO.attachExtension }</a></p>
			    	</div>
			    </c:if>
			</c:forEach>
			</div>
		</div>
	</div>

	<!-- 댓글 영역 -->
	<input type="hidden" id="boardId" value="${boardDTO.boardId }">
	
	<h2>댓글 작성</h2>
	<sec:authorize access="isAnonymous()">
		* 댓글을 작성하려면 로그인해 주세요.
	</sec:authorize>
	<sec:authorize access="isAuthenticated()">
		<div style="text-align: center;">
			<p>${user.username }</p>
			<input type="hidden" id="memberId" value="${user.username }">
			<input type="text" id="replyContent" maxlength="150">
			<button id="btnAdd">작성</button>
		</div>	
	</sec:authorize>
	

	<hr>
	<div style="text-align: center;">
		<div id="replies"></div>
	</div>
	
	<%@ include file="../common/footer.jsp" %>
	
	<script type="text/javascript">
	
	$(document).ajaxSend(function(e, xhr, opt){
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
		xhr.setRequestHeader(header, token);
	});
	
	$(document).ready(function(){
		$("#listBoard").click(function(){
			var listForm = $("#listForm"); // form 객체 참조
			
			// c:out을 이용한 현재 페이지 번호값 저장
			var pageNum = "<c:out value='${pagination.pageNum }' />";
			var pageSize = "<c:out value='${pagination.pageSize }' />"; 
			var type = "<c:out value='${pagination.type }' />";
			var keyword = "<c:out value='${pagination.keyword }' />";
			
			// 페이지 번호를 input name='pageNum' 값으로 적용
			listForm.find("input[name='pageNum']").val(pageNum);
			// 선택된 옵션 값을 input name='pageSize' 값으로 적용
			listForm.find("input[name='pageSize']").val(pageSize);
			// type 값을 적용
			listForm.find("input[name='type']").val(type);
			// keyword 값을 적용
			listForm.find("input[name='keyword']").val(keyword);
			listForm.submit(); // form 전송
		}); // end click()
		
		$("#modifyBoard").click(function(){
			var modifyForm = $("#modifyForm"); // form 객체 참조
			
			var boardId = "<c:out value='${boardDTO.boardId}' />";
			// c:out을 이용한 pagination값 저장
			var pageNum = "<c:out value='${pagination.pageNum }' />";
			var pageSize = "<c:out value='${pagination.pageSize }' />";
			var type = "<c:out value='${pagination.type }' />";
			var keyword = "<c:out value='${pagination.keyword }' />";
			
			// 게시글 번호를 input name='boardId' 값으로 적용
			modifyForm.find("input[name='boardId']").val(boardId);				
			// 페이지 번호를 input name='pageNum' 값으로 적용
			modifyForm.find("input[name='pageNum']").val(pageNum);
			// 선택된 옵션 값을 input name='pageSize' 값으로 적용
			modifyForm.find("input[name='pageSize']").val(pageSize);
			// type 값을 적용
			modifyForm.find("input[name='type']").val(type);
			// keyword 값을 적용
			modifyForm.find("input[name='keyword']").val(keyword);
			modifyForm.submit(); // form 전송
		}); // end click()
		
		$('#deleteBoard').click(function(){
			if(confirm('삭제하시겠습니까?')) {
				var deleteForm = $("#deleteForm"); // form 객체 참조
				
				var boardId = "<c:out value='${boardDTO.boardId}' />";
				// c:out을 이용한 pagination값 저장
				var pageNum = "<c:out value='${pagination.pageNum }' />";
				var pageSize = "<c:out value='${pagination.pageSize }' />"; 
				var type = "<c:out value='${pagination.type }' />";
				var keyword = "<c:out value='${pagination.keyword }' />";
				
				// 게시글 번호를 input name='boardId' 값으로 적용
				deleteForm.find("input[name='boardId']").val(boardId);				
				// 페이지 번호를 input name='pageNum' 값으로 적용
				deleteForm.find("input[name='pageNum']").val(pageNum);
				// 선택된 옵션 값을 input name='pageSize' 값으로 적용
				deleteForm.find("input[name='pageSize']").val(pageSize);
				// type 값을 적용
				deleteForm.find("input[name='type']").val(type);
				// keyword 값을 적용
				deleteForm.find("input[name='keyword']").val(keyword);
				deleteForm.submit(); // form 전송
			}
		}); // end click()
	});

	</script>
	<!-- js 파일 연결 -->
	<script src="${pageContext.request.contextPath }/resources/js/reply.js"></script>
</body>
</html>








