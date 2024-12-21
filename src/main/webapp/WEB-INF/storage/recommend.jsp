<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>OTTown</title>
    <link rel=stylesheet href="<c:url value='/css/base.css' />" type="text/css">
    <link rel=stylesheet href="<c:url value='/css/ott_recommend.css' />" type="text/css">
</head>
<body>
	<!-- 헤더 포함 -->
    <jsp:include page="../common/header.jsp" />
    <main>
    	<div class="menu">
            <a href="<c:url value='/storage/view' />" class="menu-link">보관함</a>
            <a href="<c:url value='/storage/recommend' />" class="menu-link" style="color: #000000;">OTT 분석</a>
            <p>&nbsp;&nbsp;&nbsp;선택한 작품 <strong>${totalCount}</strong>개</p>
        </div>
	    <div class="ott-list">
	  		<c:forEach items="${recommendations}" var="recommendation">
	  			<div class="ott-rec">
	  				<div class="ott-rec-div">
	  					<br><img src="${recommendation.image}" style="<c:if test='${recommendation.id == 4}'>width: 25%;</c:if>">
	  					<p>선택한 작품 ${recommendation.count}개 </p>
	                </div>
	                <!-- 공동구매방 리스트 조회 uri 참고해서 수정하기 -->
	                <input type="button" value="공동구매" onclick="location.href='<c:url value='/OTTGroupList/view?ottId=${recommendation.id}' />'">
	                <br>
	            </div>
	        </c:forEach>
	  	</div>
    </main>
</body>
</html>
