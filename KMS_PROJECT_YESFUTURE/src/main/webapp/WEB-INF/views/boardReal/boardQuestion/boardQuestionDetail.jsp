<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>

<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<!-- jquery 라이브러리 import -->
<script src="https://code.jquery.com/jquery-3.7.1.js">
</script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" 
rel="stylesheet">

<!-- css 파일 불러오기 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/image.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/attach.css">

<style type="text/css">
	    /* 경계선 스타일 */
	    .divider {
	        border: none;
	        border-top: 1px solid #000; /* 얇은 검은색 선 */
	        margin: 0;
	    }
	        
	    /* 메뉴바 스타일 */
	    .menu-bar {
	        display: flex; /* Flexbox 적용 */
	        justify-content: flex-start; /* 왼쪽부터 가로로 배치 */
	        align-items: center; /* 세로 중앙 정렬 */
	        padding: 10px 20px; /* 메뉴바 안쪽 여백 */
	    }
        
        /* 좌측 버튼 스타일 */
		.group1 {
		    display: flex;
		}
		
		/* 우측 버튼 그룹 스타일 */
		.group2 {
		    display: flex;
		    margin-left: auto; /* 우측으로 밀어붙임 */
		}
		
		        /* 버튼 사이의 간격 설정 */
        .menu-bar a {
            margin-right: 20px; /* 각 버튼 사이의 간격 (원하는 값으로 조정 가능) */
            text-decoration: none; /* 링크 밑줄 제거 */
        }

        /* 버튼 스타일 (옵션) */
        input[type="button"] {
            font-size: 16px;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        
        /* 특정 value (닉네임 버튼)에 스타일 적용 */
    	input[type="button"][value="${sessionScope.memberNickname}"] {
        background-color: transparent; /* 배경을 투명하게 설정 */
        color: #4CAF50; /* 글자 색상 설정 */
        border: 1px solid #4CAF50; /* 선택사항: 테두리 추가 */
    	}
    	
        /* 버튼에 마우스를 올렸을 때 스타일 (옵션) */
        input[type="button"]:hover {
            background-color: #45a049;
        }
        
        /* 전체 레이아웃 */
        .container {
            height: 80vh; /* 화면 높이의 80%를 사용 */
        }
        			
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
		
		.reply_item .btn_like, 
		.reply_item .btn_hate {
		    background-color: transparent; /* 버튼 배경색 제거 */
		    border: none; /* 테두리 제거 */
		    cursor: pointer; /* 클릭 가능한 포인터 표시 */
		    font-size: 18px; /* 아이콘 크기 */
		    margin-right: 5px; /* 버튼 간격 */
		}
		
		.reply_item .btn_like i {
		    color: transparent; /* 내부는 투명 */
		    -webkit-text-stroke: 1px red; /* 테두리는 빨간색 */
		    text-stroke: 1px red; /* 테두리는 빨간색 */
		}
		
		.reply_item .btn_like.liked i {
		    color: red; /* 내부 빨간색 */
		    -webkit-text-stroke: 0; /* 테두리 없음 */
		    text-stroke: 0; /* 테두리 없음 */
		}
		
		.reply_item .btn_hate i {
			color: transparent; /* 내부는 투명 */
		    -webkit-text-stroke: 1px blue; /* 테두리는 빨간색 */
		    text-stroke: 1px blue; /* 테두리는 빨간색 */
		}
		
		.reply_item .btn_like:hover i {
		    color: red; /* 좋아요 아이콘 호버 색상 */
		}
		
		.reply_item .btn_like.liked:hover i {
		    color: red; /* 좋아요 아이콘 호버 색상 */
		    -webkit-text-stroke: 1px black; /* 테두리 없음 */
		    text-stroke: 1px black; /* 테두리 없음 */
		}
		
		.reply_item .btn_hate:hover i {
		    color: blue; /* 싫어요 아이콘 호버 색상 */
		}
		
		.sub_reply_item .sub_btn_like, 
		.sub_reply_item .sub_btn_hate {
		    background-color: transparent; /* 버튼 배경색 제거 */
		    border: none; /* 테두리 제거 */
		    cursor: pointer; /* 클릭 가능한 포인터 표시 */
		    font-size: 18px; /* 아이콘 크기 */
		    margin-right: 5px; /* 버튼 간격 */
		}
		
		.sub_reply_item .sub_btn_like i {
		    color: transparent; /* 내부는 투명 */
		    -webkit-text-stroke: 1px red; /* 테두리는 빨간색 */
		    text-stroke: 1px red; /* 테두리는 빨간색 */
		}
		
		.sub_reply_item .sub_btn_like.liked i {
		    color: red; /* 내부 빨간색 */
		    -webkit-text-stroke: 0; /* 테두리 없음 */
		    text-stroke: 0; /* 테두리 없음 */
		}
		
		.sub_reply_item .sub_btn_hate i {
			color: transparent; /* 내부는 투명 */
		    -webkit-text-stroke: 1px blue; /* 테두리는 빨간색 */
		    text-stroke: 1px blue; /* 테두리는 빨간색 */
		}
		
		.sub_reply_item .sub_btn_like:hover i {
		    color: red; /* 좋아요 아이콘 호버 색상 */
		}
		
		.sub_reply_item .sub_btn_like.liked:hover i {
		    color: red; /* 좋아요 아이콘 호버 색상 */
		    -webkit-text-stroke: 1px black; /* 테두리 없음 */
		    text-stroke: 1px black; /* 테두리 없음 */
		}
		
		.sub_reply_item .sub_btn_hate:hover i {
		    color: blue; /* 싫어요 아이콘 호버 색상 */
		}
		
</style>

<meta charset="UTF-8">
<title>${boardQuestionVO.boardQuestionTitle }</title>
</head>
<body>
	<h1>질문 게시판</h1>
		
	<div class="menu-bar">
        <!-- 이 위치에 main으로 가는 버튼 만들기 -->
        <a href="problemMain" class="group1"><input type="button" value="문제은행 가기"></a>
        <a href="boardMain" class="group1"><input type="button" value="게시판"></a>
	
		<div class="group2">
			<c:if test="${empty sessionScope.memberEmail }">
			<!-- 회원 가입 버튼 -->
		    <a href="agree"><input type="button" value="${boardQuestionVO.boardQuestionId}"></a>
		    <!-- 로그인 버튼 -->
		    <a href="login"><input type="button" value="로그인"></a>
			</c:if>
			
			<c:if test="${not empty sessionScope.memberEmail }">
			<a href="mypage"><input type="button" value="${sessionScope.memberNickname}"></a>
			<a href="../board/logout"><input type="button" value="로그아웃"></a>
			</c:if>
		</div>
	</div>
	
	<!-- 가로 경계선 -->
    <hr class="divider">
    
    	<!-- 메인 레이아웃 -->
    <div class="container">
	    <div>
			<p>제목 : ${boardQuestionVO.boardQuestionTitle }</p>
		</div>
		<div>
			<p>작성자 : ${boardQuestionVO.memberNickname }</p>
			<!-- boardDateCreated 데이터 포멧 변경 -->
			<fmt:formatDate value="${boardQuestionVO.boardQuestionDateCreated }"
						pattern="yyyy-MM-dd HH:mm:ss" var="boardQuestionDateCreated"/>
			<p>작성일 : ${boardQuestionDateCreated }</p>
		</div>
		<div>
			<textarea rows="20" cols="120" readonly>${boardQuestionVO.boardQuestionContent }</textarea>
		</div>
		
		<button onclick="location.href='boardQuestion'">글 목록</button>
		<sec:authentication property="principal" var="user"/>	
		<sec:authorize access="isAuthenticated()">
			<c:if test="${boardQuestionVO.memberNickname eq user.member.memberNickname}">
				<button id="modify">글 수정</button>
				<button id="delete">글 삭제</button>		
			</c:if>
		</sec:authorize>
	</div>
	
	<form id="modifyForm" action="modifyQuestion" method="GET">
		<input type="hidden" name="boardQuestionId">
	</form>
	<form id="deleteForm" action="deleteQuestion" method="POST">
		<input type="hidden" name="boardQuestionId">
    	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
	</form>
	
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
			    	<p><a href="../../attach/download?attachQuestionId=${attachQuestionVO.attachQuestionId }">${attachQuestionVO.attachQuestionRealName }.${attachQuestionVO.attachQuestionExtension }</a></p>
			    	</div>
			    </c:if>
			</c:forEach>
			</div>
		</div>
	</div>
	
	<div style="text-align: center;">
		<input type="text" id="memberNickname" value="<sec:authentication property="principal.member.memberNickname"/>" readonly>
		<input type="hidden" id="memberId" value="<sec:authentication property="principal.member.memberId"/>" readonly>
		<input type="text" id="replyQuestionContent">
		<button id="btnAdd">댓글 달기</button>
	</div>
	
	<hr>
	
	<div style="text-align: center;">
		<div id="replies"></div>
	</div>
	
	<script type="text/javascript">
		
		$(document).ajaxSend(function(e, xhr, opt){
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			
			xhr.setRequestHeader(header, token);
		});
	
		$(document).ready(function(){
			
			// 게시글 수정
			$('#modify').click(function(){
				var modifyForm = $("#modifyForm"); // form 객체 참조
				var boardId = "<c:out value='${boardQuestionVO.boardQuestionId}' />";
				// 게시글 번호를 input name='boardQuestionId' 값으로 적용
				modifyForm.find("input[name='boardQuestionId']").val(boardId);	
				
				$('#modifyForm').submit();
			})
			
			// 게시글 삭제
			$('#delete').click(function(){
				if(confirm('삭제하시겠습니까?')){
					var deleteForm = $("#deleteForm"); // form 객체 참조
					var boardId = "<c:out value='${boardQuestionVO.boardQuestionId}' />";
					// 게시글 번호를 input name='boardQuestionId' 값으로 적용
					deleteForm.find("input[name='boardQuestionId']").val(boardId);	
					
					$('#deleteForm').submit(); // form 데이터 전송
				}
			}); // end deleteBoard
			
			getAllReply(); // 함수 호출		
			
			// 댓글 작성 기능
			$('#btnAdd').click(function(){
				var boardQuestionId = ${boardQuestionVO.boardQuestionId}; // 게시판 번호 데이터
				var memberNickname = $('#memberNickname').val(); // 작성자닉네임데이터
				var memberId = $('#memberId').val(); // 작성자Id 데이터
				var replyQuestionContent = $('#replyQuestionContent').val(); // 댓글 내용
				
				if(replyQuestionContent.trim() === '') {
					alert('내용을 입력해 주세요.');
					return;
				}
				
				// javascript 객체 생성
				var obj = {
						'boardQuestionId' : boardQuestionId,
						'memberNickname' : memberNickname,
						'memberId' : memberId,
						'replyQuestionContent' : replyQuestionContent
				}
				console.log(obj);
				
				// $.ajax로 송수신
				$.ajax({
					type : 'POST', // 메서드 타입
					url : '../../reply/replyQuestion', // url
					headers : { // 헤더 정보
						'Content-Type' : 'application/json' // json content-type 설정
					}, // 여기 'application/json'이 있어야 문제가 안생긴다.
					data : JSON.stringify(obj), // JSON으로 변환
					success : function(result) { // 전송 성공 시 서버에서 result 값 전송
						console.log(result);
						if(result == 1) {
							alert('댓글 입력 성공');
							getAllReply(); // 함수 호출		
						}
					}
				});
			}); // end btnAdd.click()

			
			// 게시판 댓글 전체 가져오기
			function getAllReply() {
				var boardQuestionId = ${boardQuestionVO.boardQuestionId};
				
				var url = '../../reply/allQuestion/' + boardQuestionId;
				
				$.getJSON(
					url, 		
					function(data) {
						// data : 서버에서 전송받은 list 데이터가 저장되어 있음.
						// getJSON()에서 json 데이터는 
						// javascript object로 자동 parsing됨.
						console.log(data);
					
						var memberNickname = $('#memberNickname').val();
						
						var list = ''; // 댓글 데이터를 HTML에 표현할 문자열 변수
						var hidden = ''; // 같으면 그대로 쓰고 다르면 글자를 넣는다
						
						// $(컬렉션).each() : 컬렉션 데이터를 반복문으로 꺼내는 함수
						$(data).each(function(){
							var isLikedByCurrentUser = this.likedByCurrentUser; // 서버에서 전달받은 좋아요정보
							console.log("isLikedByCurrentUser : " + isLikedByCurrentUser);
							// this : 컬렉션의 각 인덱스 데이터를 의미
							console.log(this);
						  	
							// 본인이 작성한 댓글만 수정, 삭제 버튼이 나오게
							if(memberNickname != this.memberNickname) {
								hidden = 'hidden';
						 	} else {
						 		hidden = '';	
						 	}
							
							var replyQuestionId = this.replyQuestionId;
							// 전송된 replyDateCreated는 문자열 형태이므로 날짜 형태로 변환이 필요
							var replyQuestionDateCreated = new Date(this.replyQuestionDateCreated);
							// 좋아요 버튼 스타일 결정
				            var likeButtonClass = isLikedByCurrentUser ? 'btn_like liked' : 'btn_like';
														
							if(this.replyQuestionEnabled === 0) {
								list += '<div class="reply_item">'
									+ '<pre>'
									+ '<input type="hidden" id="replyQuestionId" value="'+ this.replyQuestionId +'">'
									+ this.memberNickname
									+ '&nbsp;&nbsp;' // 공백
									+ '&nbsp;&nbsp;'
									+ '※ 삭제된 댓글입니다.'
									+ replyQuestionDateCreated
									+ '</pre>'
									+ '</div>';
							} else {													            
								list += '<div class="reply_item">'
									+ '<pre>'
									+ '<input type="hidden" id="replyQuestionId" value="'+ this.replyQuestionId +'">'
									+ this.memberNickname
									+ '&nbsp;&nbsp;' // 공백
									+ '&nbsp;&nbsp;'
									+ '<input type="text" id="replyQuestionContent" value="'+ this.replyQuestionContent +'" readonly>'
									+ '&nbsp;&nbsp;'
									+ '&nbsp;&nbsp;'
									+ '<span style="color:red;">'+ this.replyQuestionLike +'</span>'
									+ '&nbsp;&nbsp;'
									+ '&nbsp;&nbsp;'
									+ '<span style="color:blue;">'+ this.replyQuestionHate +'</span>'
									+ '&nbsp;&nbsp;'
									+ '&nbsp;&nbsp;'
									+ '<button class="'+ likeButtonClass +'"><i class="fas fa-thumbs-up"></i></button>'
									+ '&nbsp;&nbsp;'
									+ '<button class="btn_hate"><i class="fas fa-thumbs-down"></i></button>'
									+ '&nbsp;&nbsp;'
									+ '&nbsp;&nbsp;'
									+ replyQuestionDateCreated
									+ '&nbsp;&nbsp;'
									+ '&nbsp;&nbsp;'
									+ '<button class="btn_update" '+ hidden +'>수정</button>'
									+ '&nbsp;&nbsp;'
									+ '<button class="btn_delete" '+ hidden +'>삭제</button>'
									+ '&nbsp;&nbsp;'
									+ '<button class="btn_subReply" >답글</button>'
									+ '</pre>'
									+ '</div>';
							}
							
							
								
								var subReplyUrl = '../../subReply/allQuestion/' + replyQuestionId;
								
								$.ajax({
					                url: subReplyUrl,
					                type: 'GET',
					                async: false, // 동기식으로 처리 (순차적으로 답글을 가져오기 위해) 
					                success: function(subReplyData) {
					                    console.log(subReplyData);

										var subHidden = ''; // 같으면 그대로 쓰고 다르면 글자(hidden)를 넣는다
										
					                    list += '<div class="sub_reply_list">';

					                    // 답글 반복문
					                    $(subReplyData).each(function() {
					                    	console.log("this : " + this.memberNickname);
					                    	console.log("memberNickname : " + memberNickname);
					                    	
					                    	// 본인이 작성한 답글만 수정, 삭제 버튼이 나오게
											if(memberNickname != this.memberNickname) {
												subHidden = 'hidden';
										 	} else {
										 		subHidden = '';
										 	}
					                    	
					                    	if (this.replyQuestionId === replyQuestionId) {
						                        var subReplyQuestionDateCreated = new Date(this.subReplyQuestionDateCreated);
						                        console.log("subReplyData : " + subReplyData);
												console.log("this.subReplyQuestionEnabled : " + this.subReplyQuestionEnabled);
												
						                        if (this.subReplyQuestionEnabled === 0) {
						                        	list += '<div class="sub_reply_item">'
														+ '<pre>'
														+ '<input type="hidden" id="subReplyQuestionId" value="'+ this.subReplyQuestionId +'">'
														+ 'ㄴ' + this.memberNickname
														+ '&nbsp;&nbsp;' // 공백
														+ '&nbsp;&nbsp;'
														+ '※ 삭제된 댓글입니다.'
														+ subReplyQuestionDateCreated
														+ '</pre>'
														+ '</div>';
												} else {
						                        	list += '<div class="sub_reply_item">'
						                                + '<pre>'
						                                + '<input type="hidden" id="subReplyQuestionId" value="'+ this.subReplyQuestionId +'">'
						                                + 'ㄴ' + this.memberNickname
						                                + '&nbsp;&nbsp;'
						                                + '<input type="text" id="subReplyQuestionContent" value="'+ this.subReplyQuestionContent +'" readonly>'
						                                + '&nbsp;&nbsp;'
						                                + '<span style="color:red;">'+ this.subReplyQuestionLike +'</span>'
						                                + '&nbsp;&nbsp;'
						                                + '&nbsp;&nbsp;'
						                                + '<span style="color:blue;">'+ this.subReplyQuestionHate +'</span>'
													    + '&nbsp;&nbsp;'
													    + '&nbsp;&nbsp;'
													    + '<button class="sub_btn_like"><i class="fas fa-thumbs-up"></i></button>'
													    + '&nbsp;&nbsp;'
													    + '<button class="sub_btn_hate"><i class="fas fa-thumbs-down"></i></button>'
													    + '&nbsp;&nbsp;'
													    + '&nbsp;&nbsp;'
						                                + subReplyQuestionDateCreated
						                                + '&nbsp;&nbsp;'
						                                + '<button class="sub_btn_update" '+ subHidden +'>수정</button>'
						                                + '&nbsp;&nbsp;'
						                                + '<button class="sub_btn_delete" '+ subHidden +'>삭제</button>'
						                                + '</pre>'
						                                + '</div>';
												}
					                    	}              
					                    });

					                    list += '</div>'; // 답글 리스트 닫기
					                }
					            });

					             // 댓글 닫기
						}); // end each()
							
						$('#replies').html(list); // 저장된 데이터를 replies div 표현
					} // end function()
				); // end getJSON()
			} // end getAllReply()
			
		    // 댓글 수정 입력 폼 표시
		    $('#replies').on('click', '.btn_update', function() {
		        var replyQuestionId = $(this).closest('.reply_item').find('input#replyQuestionId').val();
		        		        
		        // 기존 댓글 수정 입력 폼이 있다면 삭제
		        $('.updateReplyForm').remove();
		
		        // 답글 입력 폼 생성
		        var updateReplyForm = `
		            <div class="updateReplyForm">
		                <input type="text" id="updateReplyContent" placeholder="수정 내용을 입력하세요">
		                <button class="btnUpdateReply" data-replyQuestionId="${replyQuestionId}">댓글 수정</button>
		            </div>
		        `;
		        $(this).after(updateReplyForm);
		    });
			
		    // 댓글 수정 버튼 클릭 이벤트
		    $('#replies').on('click', '.btnUpdateReply', function() {
		        var replyQuestionId = $(this).closest('.reply_item').find('input#replyQuestionId').val();
				var memberNickname = $('#memberNickname').val(); // 작성자닉네임데이터
				var memberId = $('#memberId').val(); // 작성자Id 데이터
		        var replyQuestionContent = $('#updateReplyContent').val();
		        
		        if (replyQuestionContent.trim() === '') {
		            alert('내용을 입력해주세요.');
		            return;
		        }
		
				// javascript 객체 생성
				var updateReplyObj = {
						'replyQuestionId' : replyQuestionId,
						'memberNickname' : memberNickname,
						'memberId' : memberId,
						'replyQuestionContent' : replyQuestionContent
				}
		
		        // AJAX 요청
		        $.ajax({
		            type: 'PUT',
		            url: '../../reply/' + replyQuestionId,
		            headers: { 'Content-Type': 'application/json' },
		            data: JSON.stringify(updateReplyObj),
		            success: function(result) {
		                if (result == 1) {
		                	alert('댓글 수정 성공');
							// 댓글, 답글 목록 출력 함수 호출
		                    // 입력 폼 제거
		                    getAllReply();
		                }
		            }
		        });
		    });
			
			// 댓글 삭제 기능
			$('#replies').on('click', '.btn_delete', function () {
			    // 현재 삭제 버튼이 속한 댓글의 부모 요소를 가져옴
			    var memberNickname = $('#memberNickname').val(); // 로그인회원 닉네임데이터
			
			    // 삭제 처리 후 서버와의 동기화 (Ajax 요청)
			    var replyQuestionId = $(this).closest('.reply_item').find('#replyQuestionId').val();
			    
			    $.ajax({
			        url: '../../reply/deleteQuestion/' + replyQuestionId, 
			        type: 'PUT',
					headers : {
						'Content-Type' : 'application/json'
					},
					data : memberNickname,
					success : function(result) {
						console.log(result);
						if(result == 1) {
							alert('댓글 삭제 성공!');
							getAllReply();
						}
					}
			    });
			});
			
		    // 대댓글 입력 폼 표시
		    $('#replies').on('click', '.btn_subReply', function() {
		        var replyQuestionId = $(this).closest('.reply_item').find('input#replyQuestionId').val();
				console.log("replyQuestionId : " + replyQuestionId);
				
		        // 기존 답글 입력 폼이 있다면 삭제
		        $('.subReplyForm').remove();
		
		        // 대댓글 입력 폼 생성
		        var subReplyForm = `
		            <div class="subReplyForm">
		                <input type="text" id="subReplyContent" placeholder="답글 내용을 입력하세요">
		                <button class="btnAddSubReply" data-replyQuestionId="${replyQuestionId}">답글 달기</button>
		            </div>
		        `;
		        $(this).after(subReplyForm);
		    });
			
		    // 대댓글 추가 버튼 클릭 이벤트
		    $('#replies').on('click', '.btnAddSubReply', function() {
		        var replyQuestionId = $(this).closest('.reply_item').find('input#replyQuestionId').val();
				var memberNickname = $('#memberNickname').val(); // 작성자닉네임데이터
				var memberId = $('#memberId').val(); // 작성자Id 데이터
		        var subReplyQuestionContent = $('#subReplyContent').val();
		        console.log("replyQuestionId : " + replyQuestionId);
		        
		        if (subReplyQuestionContent.trim() === '') {
		            alert('내용을 입력해주세요.');
		            return;
		        }
		
				// javascript 객체 생성
				var subReplyObj = {
						'replyQuestionId' : replyQuestionId,
						'memberNickname' : memberNickname,
						'memberId' : memberId,
						'subReplyQuestionContent' : subReplyQuestionContent
				}
				console.log(subReplyObj);
		
		        // AJAX 요청
		        $.ajax({
		            type: 'POST',
		            url: '../../subReply/subReplyQuestion',
		            headers: { 'Content-Type': 'application/json' },
		            data: JSON.stringify(subReplyObj),
		            success: function(result) {
		                if (result == 1) {
		                	alert('답글 입력 성공');
							// 댓글, 답글 목록 출력 함수 호출
		                    // 입력 폼 제거
		                    $('.subReplyForm').remove();
		                    getAllReply();
		                }
		            }
		        });
		    });
		    
		 	// 대댓글 삭제 기능
			$('#replies').on('click', '.sub_btn_delete', function () {
			    // 현재 삭제 버튼이 속한 댓글의 부모 요소를 가져옴
			    var memberNickname = $('#memberNickname').val(); // 로그인회원 닉네임데이터
			
			    // 삭제 처리 후 서버와의 동기화 (Ajax 요청)
			    var subReplyQuestionId = $(this).closest('.sub_reply_item').find('#subReplyQuestionId').val();
			    
			    $.ajax({
			        url: '../../subReply/deleteQuestion/' + subReplyQuestionId, 
			        type: 'PUT',
					headers : {
						'Content-Type' : 'application/json'
					},
					data : memberNickname,
					success : function(result) {
						console.log(result);
						if(result == 1) {
							alert('답글 삭제 성공!');
							getAllReply();
						}
					}
			    });
			});
			
		    // 댓글 좋아요 버튼
		    $('#replies').on('click', '.btn_like', function() {
		    	var $this = $(this); // 클릭된 버튼
		        var memberNickname = $('#memberNickname').val();
		        var replyQuestionId = $(this).closest('.reply_item').find('input#replyQuestionId').val();
		        
		     	// ajavascript 객체 생성
				var obj = {
						'replyQuestionId' : replyQuestionId,
						'memberNickname' : memberNickname,
				}
				console.log(obj);
		        
		     	// AJAX 요청
		        $.ajax({
		            type: 'POST',
		            url: '../../like/replyLike',
		            headers: { 'Content-Type': 'application/json' },
		            data: JSON.stringify(obj),
		            success: function(result) {
		            	console.log("Response from server : " + result);
		            	var $likeCountSpan = $this.closest('.reply_item')
		            								.find('span[style="color:red;"]'); // 좋아요 갯수 span
		            	var currentLikeCount = parseInt($likeCountSpan.text().trim(), 10); // 현재 좋아요 갯수
		            	
		                if (result === "success") {
		                    alert("좋아요를 눌렀습니다.");
		                    // 좋아요 버튼 UI 업데이트 (예: 버튼 색 변경)
		    				// 비동기로 좋아요, 싫어요 업데이트 or getAllReply();
		                    $this.addClass('liked');
		                    $likeCountSpan.text(currentLikeCount + 1); // 좋아요 갯수 증가
		                } else if (result === "cancel") {
		                    alert("좋아요를 취소했습니다.");
		                    // 좋아요 취소 UI 업데이트 (예: 버튼 색 복원)
		    				// 비동기로 좋아요, 싫어요 업데이트 or getAllReply();
		                    $this.removeClass('liked');
		                    $likeCountSpan.text(currentLikeCount - 1); // 좋아요 갯수 증가
		                }
		            }
		        }); // end ajax
		    });
		    
		    // 대댓글 좋아요 버튼
		    $('#replies').on('click', '.sub_btn_like', function() {
		    	var $this = $(this); // 클릭된 버튼
		        var memberNickname = $('#memberNickname').val();
		        var subReplyQuestionId = $(this).closest('.sub_reply_item').find('input#subReplyQuestionId').val();
		        
		     	// ajavascript 객체 생성
				var obj = {
						'subReplyQuestionId' : subReplyQuestionId,
						'memberNickname' : memberNickname,
				}
				console.log(obj);
		        
		     	// AJAX 요청
		        $.ajax({
		            type: 'POST',
		            url: '../../like/subReplyLike',
		            headers: { 'Content-Type': 'application/json' },
		            data: JSON.stringify(obj),
		            success: function(result) {
		            	console.log("Response from server : " + result);
		            	var $likeCountSpan = $this.closest('.sub_reply_item')
		            								.find('span[style="color:red;"]'); // 좋아요 갯수 span
		            	var currentLikeCount = parseInt($likeCountSpan.text().trim(), 10); // 현재 좋아요 갯수
		            	
		                if (result === "success") {
		                    alert("좋아요를 눌렀습니다.");
		                    // 좋아요 버튼 UI 업데이트 (예: 버튼 색 변경)
		    				// 비동기로 좋아요, 싫어요 업데이트 or getAllReply();
		                    $this.addClass('liked');
		                    $likeCountSpan.text(currentLikeCount + 1); // 좋아요 갯수 증가
		                } else if (result === "cancel") {
		                    alert("좋아요를 취소했습니다.");
		                    // 좋아요 취소 UI 업데이트 (예: 버튼 색 복원)
		    				// 비동기로 좋아요, 싫어요 업데이트 or getAllReply();
		                    $this.removeClass('liked');
		                    $likeCountSpan.text(currentLikeCount - 1); // 좋아요 갯수 증가
		                }
		            }
		        }); // end ajax
		    });
		    
		    // 싫어요 버튼
 
					    
		}); // end document
	</script>
		
</body>
</html>