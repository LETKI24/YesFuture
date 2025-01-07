<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
table, th, td {
	border-style: solid;
	border-width: 1px;
	text-align: center;
}

ul {
	list-style-type: none;
	text-align: center;
}

li {
	display: inline-block;
}

#searchForm {
    width: 50%; 
    margin: 0 auto; 
    text-align: center;
}
</style>
<!-- jquery 라이브러리 import -->
<script src="https://code.jquery.com/jquery-3.7.1.js">
</script>
<meta charset="UTF-8">
<title>게시판 메인 페이지</title>
</head>
<body>
	<%@ include file="../common/header.jsp" %>
	<div class="board-section">
		<h1>게시판</h1>
		<!-- 글 작성 페이지 이동 버튼 -->
		<a href="register"><input type="button" value="글 작성"></a>
	
		<hr>
		<!-- 게시글 페이지 사이즈 선택 -->
		<select id="selectSize">
			<option value="" disabled selected>게시글 수</option>
	        <option value="5">5</option>
	        <option value="10">10</option>
	        <option value="15">15</option>
	        <option value="20">20</option>
	    </select>
	    <br/>
	    
		<table>
			<thead>
				<tr>
					<th style="width: 60px">번호</th>
					<th style="width: 700px">제목</th>
					<th style="width: 120px">작성자</th>
					<th style="width: 100px">작성일</th>
					<th style="widt: 50px">댓글 수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="boardDTO" items="${boardList }">
					<tr>
						<td>${boardDTO.boardId }</td>
						<td class="detail_button"><a href="${boardDTO.boardId }"><span><c:out value="${boardDTO.boardTitle }" /></span></a></td>
						<td>${boardDTO.memberId }</td>
						<!-- boardDateCreated 데이터 포멧 변경 -->
						<fmt:formatDate value="${boardDTO.boardDateCreated }"
							pattern="yyyy-MM-dd HH:mm:ss" var="boardDateCreated" />
						<td>${boardDateCreated }</td>
						<td>${boardDTO.replyCount }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<form id="searchForm" action="list" method="get">
			<input type="hidden" name="pageNum">
	    	<input type="hidden" name="pageSize">
			<select name="type">
				<option value="title">제목</option>
				<option value="content">내용</option>
				<option value="writer">작성자</option>
			</select>
			<input type="text" name="keyword">
	
	    	<button>검색</button>
		</form>
		
		<!-- 게시글 번호, 페이지 번호, 페이지 사이즈를 전송하는 form  -->
		<form id="detailForm" action="detail" method="get">
			<input type="hidden" name="boardId" >
			<input type="hidden" name="pageNum" >
	    	<input type="hidden" name="pageSize" >
	    	<input type="hidden" name="type" >
			<input type="hidden" name="keyword" >
		</form>
		
		<ul>
			<!-- 이전 버튼 생성을 위한 조건문 -->
			<c:if test="${pageMaker.isPrev() }">
				<li class="pagination_button"><a href="${pageMaker.startNum - 1}">이전</a></li>
			</c:if>
			<!-- 반복문으로 시작 번호부터 끝 번호까지 생성 -->
			<c:forEach begin="${pageMaker.startNum }"
				end="${pageMaker.endNum }" var="num">
				<li class="pagination_button"><a href="${num }">${num }</a></li>
			</c:forEach>
			<!-- 다음 버튼 생성을 위한 조건문 -->
			<c:if test="${pageMaker.isNext() }">
				<li class="pagination_button"><a href="${pageMaker.endNum + 1}">다음</a></li>
			</c:if>
		</ul>
		
		<!-- 페이지 번호와 페이지 사이즈를 전송하는 form -->
		<form id="listForm" action="list" method="get">
	    	<input type="hidden" name="pageNum" >
	    	<input type="hidden" name="pageSize" >
	    	<input type="hidden" name="type">
			<input type="hidden" name="keyword">
	    </form>
	    
	</div>
	<hr>
	<%@ include file="../common/footer.jsp" %>
	
	<script type="text/javascript">
		$(document).ready(function(){
			
			// select 옵션을 선택하면 이벤트 발생
			$("#selectSize").on("change", function(e){
				var listForm = $("#listForm"); // form 객체 참조
				console.log(this);
				// c:out을 이용한 현재 페이지 번호값 저장
				var pageNum = "<c:out value='${pageMaker.pagination.pageNum }' />";
				var pageSize = $(this).val(); // 선택된 값을 저장
				
				var type = "<c:out value='${pageMaker.pagination.type }' />";
				var keyword = "<c:out value='${pageMaker.pagination.keyword }' />";
				 
				// 페이지 번호를 input name='pageNum' 값으로 적용
				listForm.find("input[name='pageNum']").val(pageNum);
				// 선택된 옵션 값을 input name='pageSize' 값으로 적용
				listForm.find("input[name='pageSize']").val(pageSize);
				// type 값을 적용
				listForm.find("input[name='type']").val(type);
				// keyword 값을 적용
				listForm.find("input[name='keyword']").val(keyword);
				
				listForm.submit(); // form 전송
			}); // end on()
			
			// pagination_button을 클릭하면 페이지 이동
			$(".pagination_button a").on("click", function(e){
				var listForm = $("#listForm"); // form 객체 참조
				e.preventDefault(); // a 태그 이벤트 방지
			
				var pageNum = $(this).attr("href"); // a태그의 href 값 저장
				// 현재 페이지 사이즈값 저장
				var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
				var type = "<c:out value='${pageMaker.pagination.type }' />";
				var keyword = "<c:out value='${pageMaker.pagination.keyword }' />";
				 
				// 페이지 번호를 input name='pageNum' 값으로 적용
				listForm.find("input[name='pageNum']").val(pageNum);
				// 선택된 옵션 값을 input name='pageSize' 값으로 적용
				listForm.find("input[name='pageSize']").val(pageSize);
				// type 값을 적용
				listForm.find("input[name='type']").val(type);
				// keyword 값을 적용
				listForm.find("input[name='keyword']").val(keyword);
				listForm.submit(); // form 전송
			}); // end on()
			
			// detail_button을 클릭하면 페이지 이동
			$(".detail_button a").on("click", function(e){
				var detailForm = $("#detailForm");
				e.preventDefault(); // a 태그 이벤트 방지
			
				var boardId = $(this).attr("href"); // a태그의 href 값 저장

				var type = "<c:out value='${pageMaker.pagination.type }' />";
				var keyword = "<c:out value='${pageMaker.pagination.keyword }' />";
				var pageNum = "<c:out value='${pageMaker.pagination.pageNum }' />";
				// 현재 페이지 사이즈값 저장
				var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
				 
				// 클릭된 게시글 번호를 input name='boardId' 값으로 적용
				detailForm.find("input[name='boardId']").val(boardId);
				// 페이지 번호를 input name='pageNum' 값으로 적용
				detailForm.find("input[name='pageNum']").val(pageNum);
				// 선택된 옵션 값을 input name='pageSize' 값으로 적용
				detailForm.find("input[name='pageSize']").val(pageSize);
				// type 값을 적용
				detailForm.find("input[name='type']").val(type);
				// keyword 값을 적용
				detailForm.find("input[name='keyword']").val(keyword);
				detailForm.submit(); // form 전송
			}); // end on()
			
			
			$("#searchForm button").on("click", function(e){
				var searchForm = $("#searchForm");
				e.preventDefault(); // a 태그 이벤트 방지
				
				var keywordVal = searchForm.find("input[name='keyword']").val();
				console.log(keywordVal);
				if(keywordVal == '') {
					alert('검색 내용을 입력하세요.');
					return;
				}
				
				var pageNum = 1; // 검색 후 1페이지로 고정
				// 현재 페이지 사이즈값 저장
				var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
				 
				// 페이지 번호를 input name='pageNum' 값으로 적용
				searchForm.find("input[name='pageNum']").val(pageNum);
				// 선택된 옵션 값을 input name='pageSize' 값으로 적용
				searchForm.find("input[name='pageSize']").val(pageSize);
				searchForm.submit(); // form 전송
			}); // end on()
			
		}); // end document()
	</script>
</body>
</html>















