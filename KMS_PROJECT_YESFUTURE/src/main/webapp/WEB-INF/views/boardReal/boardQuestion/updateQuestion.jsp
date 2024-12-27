<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${boardQuestionVO.boardQuestionTitle }</title>
</head>
<body>
	<h2>질문글 수정</h2>
	<form action="updateQuestion" method="POST">
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
		<div>
			<input type="submit" value="이대로 수정하기">
		</div>
	</form>
</body>
</html>