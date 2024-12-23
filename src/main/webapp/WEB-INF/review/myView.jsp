<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>OTTown</title>
    <link rel=stylesheet href="<c:url value='/css/base.css' />" type="text/css">
    <link rel="stylesheet" href="<c:url value='/css/review.css' />" type="text/css">
</head>
<body>
   <jsp:include page="../common/header.jsp" />
   <main>
       <!-- 리뷰 섹션 -->
       <div class="review-section">
           <h2><c:out value="${consumerName}" default="이름 없음" />님의 리뷰</h2>
   
           <!-- 리뷰 리스트 -->
           <c:forEach var="review" items="${reviewList}">
			    <div class="review-box" data-review-id="${review.reviewId}" data-content-id="${review.content.contentId}">
			        <div class="review-header">
			            <div class="review-left">
			                <a href="<c:url value='/content/review/view'>
			                                 <c:param name='contentId' value='${review.content.contentId}'/>
			                             </c:url>" class="review-content-title">${review.content.title}</a>
			                <span class="review-rating">★ ${review.rating}</span>
			            </div>
			            <div class="review-right">
			                <a href="#" class="review-update">수정</a>
			                <a href="<c:url value='/review/delete'>
			                                 <c:param name='reviewId' value='${review.reviewId}'/>
			                             </c:url>" class="review-delete">삭제</a>
			            </div>
			        </div>
			        <p class="review-text">${review.reviewText}</p>
			    </div>
			</c:forEach>
			
			<!-- 리뷰 작성 폼 -->
           <form action="<c:url value='/review/update' />" method="post" id="reviewForm" class="hidden-form review-form">
           	  <input type="hidden" name="reviewId" id="hiddenReviewId" value="" />
    		  <a href="<c:url value='/content/review/view'/>" class="review-content-title" id ="review-content-title"></a><p>
              <label for="rating">평점:</label>
              <input type="number" name="rating" id="rating" min="1" max="5" step="0.5" placeholder="1~5" required>
              <textarea name="reviewText" id = "reviewText"></textarea>
              <button type="submit">리뷰 수정</button>
           </form>
       </div>
    </main>
    <script>
	    document.querySelectorAll(".review-update").forEach((link) => {
	        link.addEventListener("click", (event) => {
	            event.preventDefault(); // 기본 동작 방지
	
	            // 클릭된 링크의 부모 요소에서 데이터 가져오기
	            const reviewBox = event.target.closest(".review-box");
	            const reviewId = reviewBox.dataset.reviewId;
	            const contentId = reviewBox.dataset.contentId;
	            const title = reviewBox.querySelector(".review-content-title").textContent.trim(); // 콘텐츠 제목 가져오기
	            const rating = reviewBox.querySelector(".review-rating").textContent.replace('★', '').trim(); // 평점 가져오기
	            const reviewText = reviewBox.querySelector(".review-text").textContent.trim(); // 리뷰 내용 가져오기
	
	            const form = document.getElementById("reviewForm");
	            form.classList.toggle("hidden-form");
	            document.getElementById("hiddenReviewId").value = reviewId;
	            document.getElementById("review-content-title").textContent = title; // 제목 설정
	            document.getElementById("rating").value = rating; // 평점 설정
	            document.getElementById("reviewText").value = reviewText; // 리뷰 내용 설정
	            
	            
	            const reviewContentTitle = document.getElementById("review-content-title");
	            const baseUrl = "/content/review/view"; // 기본 URL
	            reviewContentTitle.href = `${baseUrl}?contentId=${contentId}`;
	        });
	    });
     </script>
</body>
</html>
