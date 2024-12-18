<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>콘텐츠 상세 페이지</title>
    <link rel="stylesheet" href="<c:url value='/css/review.css' />" type="text/css">
	<link rel=stylesheet href="<c:url value='/css/base.css' />"
	type="text/css">
</head>
<body>
	<jsp:include page="../common/header.jsp" />
    <!-- 콘텐츠 정보 섹션 -->
    <div class="content-container">
        <div class="content-header">
            <img src="${content.image}" alt="${content.title}" class="content-poster">
            <div class="content-details">
                <h1>${content.title}</h1>
                <div class="rating">
                    <span>평균 평점: ${content.rating} ★</span>
                </div>
            </div>
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
		    <input type="hidden" name="consumerId" value="${sessionScope.consumerId}" />
            <label for="rating">평점:</label>
            <input type="number" name="rating" id="rating" min="1" max="5" placeholder="1~5" required>
            <input type="hidden" name="contentId" value="${content.contentId}" />
            <textarea name="reviewText" placeholder="리뷰를 작성해주세요"></textarea>
            <button type="submit">리뷰 등록</button>
        </form>

        <!-- 리뷰 리스트 -->
        <c:forEach var="review" items="${reviewList}">
            <div class="review-box">
                <div class="review-header">
                    <span class="review-author">${review.consumerName}</span>
                    <span class="review-rating">★ ${review.rating}</span>
                </div>
                <p class="review-text">${review.reviewText}</p>
            </div>
        </c:forEach>
    </div>

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
