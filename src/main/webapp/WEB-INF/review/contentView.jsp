<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>콘텐츠 상세 페이지</title>
    <link rel="stylesheet" href="<c:url value='/css/review.css' />" type="text/css">
    <link rel=stylesheet href="<c:url value='/css/base.css' />" type="text/css">
</head>
<body>
   <jsp:include page="../common/header.jsp" />
   <main>
       <!-- 콘텐츠 정보 섹션 -->
       <div class="content-container">
           <!-- 이미지 -->
          <img src="${content.image}" alt="{content.title}" class="content-poster" />
      
          <!-- 작품 정보 -->
          <div class="content-info">
              <h1 class="content-title">${content.title}</h1>
              <p class="content-rating">평균 평점: ★ ${meanRating} (${count})</p>
              <p class="content-publishdate">${content.publishDate}</p>
          </div>
       </div>
   
       <!-- 리뷰 섹션 -->
       <div class="review-section">
           <h2>리뷰</h2>
           
           <!-- 리뷰 작성 버튼 -->
           <button id="toggleFormBtn" class="write-review-btn">리뷰 작성하기</button>
           
           <!-- 리뷰 작성 폼 -->
           <form action="<c:url value='/review/add' />" method="post" id="reviewForm" class="hidden-form review-form">
           	  <input type="hidden" name="contentId" value="${content.contentId}" />
              <label for="rating">평점:</label>
              <input type="number" name="rating" id="rating" min="1" max="5" step="0.5" placeholder="1~5" required>
              <textarea name="reviewText" placeholder="리뷰를 작성해주세요"></textarea>
              <button type="submit">리뷰 등록</button>
           </form>
   
           <!-- 리뷰 리스트 -->
           <c:forEach var="review" items="${reviewList}">
               <div class="review-box">
                   <div class="review-header">
                       <a href="<c:url value='/consumer/review/view'>
				            		<c:param name='consumerId' value='${review.consumer.consumerId}'/>
				         		</c:url>" class="review-author">${review.consumer.consumerName}</a>
                       <span class="review-rating">★ ${review.rating}</span>
                   </div>
                   <p class="review-text">${review.reviewText}</p>
               </div>
           </c:forEach>
       </div>
    </main>

    <!-- JavaScript -->
    <script>
        // 리뷰 작성 폼 보이기/숨기기
        document.getElementById("toggleFormBtn").addEventListener("click", function () {
            const form = document.getElementById("reviewForm");
            form.classList.toggle("hidden-form");
        });
    </script>
</body>
</html>
