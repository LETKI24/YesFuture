<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	테스트 용입니다.


	<div id="commentList">
		<div class="comment-item" data-comment-id="1">
			<p>댓글 내용 1</p>
			<button class="btnReplyForm">답글 작성</button>
			<div class="replyList"></div>
		</div>

		<div class="comment-item" data-comment-id="2">
			<p>댓글 내용 2</p>
			<button class="btnReplyForm">답글 작성</button>
			<div class="replyList"></div>
		</div>
	</div>
	
	<script type="text/javascript">
	
		$(document).ready(function() {
			    // 답글 입력 폼 표시
			    $('#commentList').on('click', '.btnReplyForm', function() {
			        var commentId = $(this).closest('.comment-item').data('comment-id');
			
			        // 기존 답글 입력 폼이 있다면 삭제
			        $('.replyForm').remove();
			
			        // 답글 입력 폼 생성
			        var replyForm = `
			            <div class="replyForm">
			                <input type="text" class="replyContent" placeholder="답글 내용을 입력하세요">
			                <button class="btnAddReply" data-comment-id="${commentId}">답글 달기</button>
			            </div>
			        `;
			        $(this).after(replyForm);
			    });
			
			    // 답글 추가 버튼 클릭 이벤트
			    $('#commentList').on('click', '.btnAddReply', function() {
			        var commentId = $(this).data('comment-id');
			        var replyContent = $(this).siblings('.replyContent').val().trim();
			
			        if (replyContent === '') {
			            alert('내용을 입력해주세요.');
			            return;
			        }
			
			        var replyData = {
			            commentId: commentId,
			            replyContent: replyContent
			        };
			
			        // AJAX 요청
			        $.ajax({
			            type: 'POST',
			            url: '../../reply/addReply',
			            headers: { 'Content-Type': 'application/json' },
			            data: JSON.stringify(replyData),
			            success: function(response) {
			                if (response) {
			                    // 응답 데이터에 따라 동적으로 답글 추가
			                    var newReply = `
			                        <div class="reply-item">
			                            <p>${response.replyContent}</p>
			                        </div>
			                    `;
			                    $('.comment-item[data-comment-id="' + commentId + '"] .replyList').append(newReply);
			
			                    // 입력 폼 제거
			                    $('.replyForm').remove();
			                }
			            },
			            error: function(err) {
			                console.error('Error:', err);
			                alert('답글 작성 중 오류가 발생했습니다.');
			            }
			        });
			    });
			});
	</script>

</body>
</html>