<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>OTT 서비스 목록</title>
    <link rel="stylesheet" href="<c:url value='/css/base.css' />" type="text/css">
    <link rel="stylesheet" href="<c:url value='/css/ottList.css' />">
</head>
<body>
    <!-- 헤더 포함 -->
    <jsp:include page="../common/header.jsp" />

    <main>
        <div class="ott-container">
            <!-- Netflix -->
            <div class="ott-card" onclick="location.href='<c:url value='/ottGroupList/OTTGroupList?ottId=1' />'">
                <img class="ott-logo" src="<c:url value='/images/netflix.png' />" alt="Netflix">
                <div class="ott-name">▶</div>
            </div>

            <!-- Tving -->
            <div class="ott-card" onclick="location.href='<c:url value='/ottGroupList/OTTGroupList?ottId=2' />'">
                <img class="ott-logo" src="<c:url value='/images/tving.svg' />" alt="Tving">
                <div class="ott-name">▶</div>
            </div>

            <!-- Coupang Play -->
            <div class="ott-card" onclick="location.href='<c:url value='/ottGroupList/OTTGroupList?ottId=3' />'">
                <img class="ott-logo" src="<c:url value='/images/coupang.webp' />" alt="Coupang Play">
                <div class="ott-name">▶</div>
            </div>

            <!-- Disney+ -->
            <div class="ott-card" onclick="location.href='<c:url value='/ottGroupList/OTTGroupList?ottId=4' />'">
                <img class="ott-logo" src="<c:url value='/images/disney.svg' />" alt="Disney+">
                <div class="ott-name">▶</div>
            </div>

            <!-- Wavve -->
            <div class="ott-card" onclick="location.href='<c:url value='/ottGroupList/OTTGroupList?ottId=5' />'">
                <img class="ott-logo" src="<c:url value='/images/wavve.png' />" alt="Wavve">
                <div class="ott-name">▶</div>
            </div>

            <!-- Watcha -->
            <div class="ott-card" onclick="location.href='<c:url value='/ottGroupList/OTTGroupList?ottId=6' />'">
                <img class="ott-logo" src="<c:url value='/images/watcha.png' />" alt="Watcha">
                <div class="ott-name">▶</div>
            </div>
        </div>
    </main>
</body>
</html>