<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>OTTown 공동구매</title>
    <link rel="stylesheet" href="<c:url value='/css/base.css' />" type="text/css">
    <link rel="stylesheet" href="<c:url value='/css/ottGroupList.css' />" type="text/css">
</head>
<body>
    <!-- 헤더 포함 -->
    <jsp:include page="../common/header.jsp" />

    <main>
        <!-- 고정된 로고와 방 개설 버튼 -->
        <div class="fixed-header">
            <img src="<c:url value='/images/${ottService.image}' />" alt="${ottService.name}" class="logo">
            <a href="<c:url value='/ottGroup/show/createForm?ottId=${ottService.id}' />" class="btn-room-create">방 개설하기</a>
        </div>

        <!-- 스크롤 가능한 공동구매방 리스트 -->
        <div class="group-room">
            <c:forEach var="room" items="${ottGroupList}">
                <div class="room" onclick="location.href='<c:url value='/ottGroup/joinGroup?groupId=${room.groupId}&serviceId=${ottService.id }' />'">
                    <div>
                        <!-- 호스트 이름을 hostNames 맵에서 가져오기 -->
                        <span class="name">${hostNames[room.groupId]}</span> 님의 공동구매방
                    </div>
                    <div class="count">${room.currentMembers} / 4</div>
                </div>
            </c:forEach>
        </div>
    </main>
</body>
</html>