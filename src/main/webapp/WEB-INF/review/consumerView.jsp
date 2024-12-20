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
       <!-- 리뷰 섹션 -->
       <div class="review-section">
           <h2>${consumerName}님의 리뷰</h2>
   
           <!-- 리뷰 리스트 -->
           <c:forEach var="review" items="${reviewList}">
               <div class="review-box">
                   <div class="review-header">
                       <div class="review-left">
			                <a href="<c:url value='/content/review/view'>
			                                 <c:param name='contentId' value='${review.content.contentId}'/>
			                             </c:url>" class="review-content-title">${review.content.title}</a>
			                <span class="review-rating">★ ${review.rating}</span>
			            </div>
                   </div>
                   <p class="review-text">${review.reviewText}</p>
               </div>
           </c:forEach>
       </div>
    </main>
</body>
</html>
