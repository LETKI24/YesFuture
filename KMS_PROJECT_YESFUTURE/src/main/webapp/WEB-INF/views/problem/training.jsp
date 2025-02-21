<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>

<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

    <meta charset="UTF-8">
    <title>Training</title>
    <style>
        /* 선택된 옵션 버튼에 대한 스타일 */
        .selected {
            background-color: #4CAF50;
            color: white;
        }
    </style>
    
    <script type="text/javascript">  
    
		$(document).ajaxSend(function(e, xhr, opt){
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			
			xhr.setRequestHeader(header, token);
		});
		
        // 각 문제의 옵션 버튼 클릭 시 호출 – 선택된 옵션을 표시
        function selectOption(problemIndex, optionIndex) {
            console.log("문제 " + (problemIndex + 1) + "의 선택된 옵션: " + (optionIndex + 1));
            // 해당 문제(div) 내 모든 옵션 버튼에서 선택 효과 제거 후, 클릭한 버튼에 효과 추가
            const problemDiv = document.getElementById('problem-' + problemIndex);
            const optionButtons = problemDiv.getElementsByClassName('option-button');
            for (let i = 0; i < optionButtons.length; i++) {
                optionButtons[i].classList.remove('selected');
            }
            const selectedButton = document.getElementById('problem-' + problemIndex + '-option-' + optionIndex);
            if (selectedButton) {
                selectedButton.classList.add('selected');
            }
        }
		
		$(document).ready(function(){
    
	        // 현재 화면에 보여지는 문제의 인덱스 (0부터 시작)
	        let currentProblemIndex = 0;
	        
	        // 지정한 인덱스의 문제(div)만 보이도록 처리
	        function showProblem(index) {
	            // 모든 문제 div를 숨김 처리
	            const problems = document.getElementsByClassName('problem');
	            for (let i = 0; i < problems.length; i++) {
	                problems[i].style.display = 'none';
	            }
	            // 선택한 문제(div)가 있으면 표시
	            const selected = document.getElementById('problem-' + index);
	            if (selected) {
	                selected.style.display = 'block';
	            }
	        }
	        

	        
	        // "다음" 버튼 클릭 시 호출 – 다음 문제로 전환
	        $('#nextProblem').click(function(){
	            const currentProblemDiv = document.getElementById('problem-' + currentProblemIndex);
	            // data 속성에서 문제의 ID를 가져옴
	            const problemId = currentProblemDiv.getAttribute('data-problem-id');
	            // 현재 문제에서 선택된 옵션 버튼을 찾음
	            const selectedButton = currentProblemDiv.querySelector('.option-button.selected');
				
	            // 선택된 옵션의 내용을 data 속성에서 가져옴
	            
	            const totalProblems = document.getElementsByClassName('problem').length;
	            
	            if (!selectedButton) {
	                if (currentProblemIndex < totalProblems - 1) {
	                    currentProblemIndex++;
	                    showProblem(currentProblemIndex);
	                } else {
	                    alert('마지막 문제입니다.');
	                }
	            } else {
	            	let optionContent = selectedButton.getAttribute('data-option-content');
	            	console.log(optionContent);
	            	
	                // 사용자 응답 객체 생성
	                const response = {
	                    'problemId' : problemId,
	                    'optionContent' : optionContent
	                };
	                console.log(response);
	                
	             	// $.ajax로 송수신
					$.ajax({
						type : 'PUT', // 메서드 타입
						url : '../response/saveResponse', // url
						headers : { // 헤더 정보
							'Content-Type' : 'application/json' // json content-type 설정
						}, // 여기 'application/json'이 있어야 문제가 안생긴다.
						data : JSON.stringify(response), // JSON으로 변환
						success : function(result) { // 전송 성공 시 서버에서 result 값 전송
							console.log(result);
							if(result == 1) {
				                if (currentProblemIndex < totalProblems - 1) {
				                    currentProblemIndex++;
				                    showProblem(currentProblemIndex);
				                } else {
				                    alert('마지막 문제입니다.');
				                }		
							} else {
								alert('문제생김');
							}
						}
					}); // end ajax
	             	
	            }     
	
	        }); // end nextProblem.click()
	        
	        // "다음" 버튼 클릭 시 호출 – 다음 문제로 전환
	        $('#prevProblem').click(function(){
	            const currentProblemDiv = document.getElementById('problem-' + currentProblemIndex);
	            // data 속성에서 문제의 ID를 가져옴
	            const problemId = currentProblemDiv.getAttribute('data-problem-id');
	            // 현재 문제에서 선택된 옵션 버튼을 찾음
	            const selectedButton = currentProblemDiv.querySelector('.option-button.selected');
				
	            // 선택된 옵션의 내용을 data 속성에서 가져옴
	            
	            const totalProblems = document.getElementsByClassName('problem').length;
	            
	            if (!selectedButton) {
	                if (currentProblemIndex > 0) {
	                    currentProblemIndex--;
	                    showProblem(currentProblemIndex);
	                } else {
	                    alert('첫 번째 문제입니다.');
	                }
	            } else {
	            	let optionContent = selectedButton.getAttribute('data-option-content');
	            	console.log(optionContent);
	            	
	                // 사용자 응답 객체 생성
	                const response = {
	                    'problemId' : problemId,
	                    'optionContent' : optionContent
	                };
	                console.log(response);
	                
	             	// $.ajax로 송수신
					$.ajax({
						type : 'PUT', // 메서드 타입
						url : '../response/saveResponse', // url
						headers : { // 헤더 정보
							'Content-Type' : 'application/json' // json content-type 설정
						}, // 여기 'application/json'이 있어야 문제가 안생긴다.
						data : JSON.stringify(response), // JSON으로 변환
						success : function(result) { // 전송 성공 시 서버에서 result 값 전송
							console.log(result);
							if(result == 1) {
				                if (currentProblemIndex > 0) {
				                    currentProblemIndex--;
				                    showProblem(currentProblemIndex);
				                } else {
				                    alert('첫 번째 문제입니다.');
				                }		
							} else {
								alert('문제생김');
							}
						}
					}); // end ajax
	             	
	            }     
	
	        }); // end prevProblem.click()
	        
	        // "문제 목록" 버튼 클릭 시 호출 (여기서는 예시로 alert 처리)
	        function showProblemList() {
	            alert('문제 목록 버튼 클릭됨');
	        }
	        
	        // 페이지 로드 시 첫 번째 문제를 표시
	        window.onload = function() {
	            showProblem(currentProblemIndex);
	        }
	        
		}); // end document	       
    </script>
</head>
<body>

	
	<!-- 최종 제출을 위한 숨겨진 form -->
    <form id="responseForm" action="trainingResult" method="POST">
        <input type="hidden" name="responseData" id="responseData" value="" />
        <div>
			<!-- 스프링 시큐리티를 사용하면 모든 post 전송에 csrf 토큰을 추가해야 함 -->
			<input type="hidden" name="${_csrf.parameterName }"
				value="${_csrf.token }">
		</div>
    </form>

    <div id="training-container">
        <!-- JSTL forEach를 이용하여 각 문제를 div로 출력 (처음에는 모두 display:none; 처리) -->
        <c:forEach var="problem" items="${problemList}" varStatus="status">
            <div id="problem-${status.index}" class="problem" 
            	data-problem-id="${problem.problemId}" style="display: none;">
                <h2>문제 ${status.index + 1}</h2>
                <p>${problem.problemContent}</p>
                
                	<!-- 이미지 파일 영역 -->
				<div class="image-upload">
					<div class="image-view">
						<div class="image-list">
							<!-- 이미지 파일 처리 코드 -->
							<c:forEach var="attachProblemVO" items="${problem.attachProblemList}">
							    <c:if test="${attachProblemVO.attachProblemExtension eq 'jpg' or 
							    			  attachProblemVO.attachProblemExtension eq 'jpeg' or 
							    			  attachProblemVO.attachProblemExtension eq 'png' or 
							    			  attachProblemVO.attachProblemExtension eq 'gif'}">
							        <div class="image_item">
							        	<!-- <a href="../imageProblem/get?attachProblemId=${attachProblemVO.attachProblemId }"
							        		 target="_blank"> -->
							        	<!-- 아래 img는 썸네일 관련 코드 -->
								        <img width="300px" height="300px" 
								        src="../imageProblem/get?attachProblemId=${attachProblemVO.attachProblemId }
								        	&attachProblemExtension=${attachProblemVO.attachProblemExtension}"/>
								        </a>
							        </div>
							    </c:if>
							</c:forEach>
						</div>
					</div>
				</div>
				
				<br>
				<br>
                
                <div class="options">
                    <!-- 각 문제의 객관식 보기 5개를 버튼으로 출력 -->
                    <c:forEach var="option" items="${problem.optionList}" varStatus="optStatus">
                        <button type="button" class="option-button" 
                                id="problem-${status.index}-option-${optStatus.index}" 
                                data-option-content="${option.optionContent}"
                                onclick="selectOption(${status.index}, ${optStatus.index})">
                            ${optStatus.index + 1}번 ${option.optionContent}
                        </button>
                        <br>
                        <br>
                        <br>
                    </c:forEach>
                </div>
                
            </div>
        </c:forEach>
    </div>
    
    <!-- 하단의 페이지네이션 버튼들 -->
    <div id="pagination" style="margin-top: 50px;">
        <button type="button" id="prevProblem">이전</button>
        <button type="button" id="nextProblem">다음</button>
        <button type="button" id="submitBtn">제출하기</button>
	        <script>
			  document.getElementById("submitBtn").addEventListener("click", function() {
			    if (confirm("이대로 문제풀이를 종료하고 채점하시겠습니까?")) {
			      // '예'를 선택한 경우 trainingResult.jsp로 이동
			      window.location.href = "trainingResult";
			    }
			    // '아니오'를 선택하면 아무 동작도 하지 않습니다.
			  });
			</script>
        
        <button type="button" onclick="showProblemList()">문제 목록</button>
    </div>
    
    
    
</body>
</html>