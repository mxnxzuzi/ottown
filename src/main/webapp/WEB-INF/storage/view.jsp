<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>OTTown</title>
    <link rel=stylesheet href="<c:url value='/css/base.css' />" type="text/css">
    <link rel=stylesheet href="<c:url value='/css/storage.css' />" type="text/css">
</head>
<body>
    <!-- 헤더 포함 -->
    <jsp:include page="../common/header.jsp" />
    <main>
        <div class="menu">
	        <div>
	            <!-- 보관함 전체 조회 -->
	            <a href="<c:url value='/storage/view' />" class="menu-link" style="color: #000000;">보관함</a>
	            <!-- OTT 추천 -->
	            <a href="<c:url value='/storage/recommend' />" class="menu-link">OTT 추천</a>
	        </div>
            <!-- 필터 -->
            <form action="<c:url value='/storage/view/filter' />" method="get" class="filter-box">
                <select name="filterkey">
                    <option value="">OTT</option>
                    <option value="netflix">넷플릭스</option>
                    <option value="tving">티빙</option>
                    <option value="coupangplay">쿠팡플레이</option>
                    <option value="disneyplus">디즈니플러스</option>
                    <option value="wavve">웨이브</option>
                    <option value="watcha">왓챠</option>
                </select>
                <button type="submit">적용</button>
            </form>
        </div>
        <div class="storage">
            <c:choose>
                <c:when test="${not empty storage}">
                    <!-- 컨텐츠 출력 -->
                    <c:forEach items="${storage}" var="content">
                        <div class="storage-list">
                            <img src="${content.image}" alt="${content.title}">
                            <p>${content.title}</p>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <!-- 컨텐츠가 없을 경우 -->
                    <p>보관함에 작품이 없습니다.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </main>
</body>
</html>
