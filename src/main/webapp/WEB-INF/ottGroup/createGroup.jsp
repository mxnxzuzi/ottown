<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>방 개설하기</title>
    <link rel="stylesheet" href="<c:url value='/css/base.css' />" type="text/css">
    <link rel="stylesheet" href="<c:url value='/css/createGroup.css' />" type="text/css">
</head>
<body>
    <!-- 헤더 포함 -->
    <jsp:include page="../common/header.jsp" />

    <main>
        <div class="container">
            <!-- OTT 로고 -->
            <div class="logo-container">
                <img src="<c:url value='/images/${ottService.image}' />" alt="${ottService.name}">
            </div>

            <!-- 방 개설 폼 -->
            <form action="<c:url value='/ottGroup/createGroup' />" method="post" class="create-room-form">
			    <input type="hidden" name="ottId" value="${ottService.id}" />
			    <div class="form-group">
			        <input type="text" id="account" name="account" placeholder="계좌번호" required>
			    </div>
			    <div class="form-group">
			        <input type="text" id="kakaoId" name="kakaoId" placeholder="카카오톡 아이디" required>
			    </div>
			    <div class="form-group">
			        <input type="text" id="ottId" name="ottId" placeholder="OTT 아이디" required>
			    </div>
			    <div class="form-group">
			        <input type="password" id="ottPassword" name="ottPassword" placeholder="OTT 비밀번호" required>
			    </div>
			    <button type="submit" class="btn-submit">방 개설하기</button>
			</form>

        </div>
    </main>
</body>
</html>
