<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>OTTown</title>
    <link rel="stylesheet" href="<c:url value='/css/base.css' />" type="text/css">
    <link rel="stylesheet" href="<c:url value='/css/ottGroup_host.css' />" type="text/css">
    <script>
        function handleAccountShare(button) {
            if (!button.classList.contains('active')) return;
            button.classList.remove('active');
            button.classList.add('shared');
            button.disabled = true;
        }
    </script>
</head>
<body>
    <!-- 헤더 포함 -->
    <jsp:include page="../common/header.jsp" />

    <main>
        <!-- 상단 정보 -->
        <div class="room-top">
            <img src="<c:url value='/images/${ottService.image}' />" alt="${ottService.name} Logo">
            <div class="room-top-box">
                <div class="room-top-name">
                    <p class="room-host-name">${host}</p>
                    <p>님의 공동구매방</p>
                </div>
                <p class="room-count">${ottGroup.currentMembers} / 4</p>
            </div>
        </div>

        <!-- 방 정보 -->
        <!-- 방 정보 -->
		<div class="room-middle">
		    <div class="room-middle-box">
		        <div class="room-inform">
		            <p>계좌번호</p>
		            <p>카톡 아이디</p>
		            <p>OTT 아이디</p>
		            <p>OTT 비밀번호</p>
		        </div>
		        <div class="room-line"></div>
		        <div class="room-inform">
		            <p>${ottGroup.account}</p>
		            <p>${ottGroup.kakaoId}</p>
		            <p>${ottGroup.ottId}</p> <!-- 호스트에게 항상 표시 -->
		            <p>${ottGroup.ottPassword}</p> <!-- 호스트에게 항상 표시 -->
		        </div>
		    </div>
		</div>


        <!-- 계정 공유 버튼 -->
		<div class="room-bottom">
		    <c:choose>
		        <c:when test="${ottGroup.checked == 1}">
		            <input type="button" value="계정 공유" class="account-share shared" disabled />
		        </c:when>
		        <c:otherwise>
		            <form action="<c:url value='/ottGroup/share' />" method="post">
		                <input type="hidden" name="groupId" value="${ottGroup.id}" />
		                <input type="submit" value="계정 공유" class="account-share active" />
		            </form>
		        </c:otherwise>
		    </c:choose>
		</div>


    </main>
</body>
</html>
