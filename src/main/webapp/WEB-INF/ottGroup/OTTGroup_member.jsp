<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>OTTown 공동구매방</title>
<link rel="stylesheet" href="<c:url value='/css/base.css' />"
	type="text/css">
<link rel="stylesheet" href="<c:url value='/css/ottGroup_member.css' />"
	type="text/css">
</head>
<body>
	<!-- 헤더 포함 -->
	<jsp:include page="../common/header.jsp" />

	<main>
		<!-- 성공 및 오류 메시지 -->
		<c:if test="${not empty successMessage}">
			<div class="success-message">${successMessage}</div>
		</c:if>
		<c:if test="${not empty errorMessage}">
			<div class="error-message">${errorMessage}</div>
		</c:if>

		<!-- 상단 정보 -->
		<div class="room-top">
			<h2>${ottService.name}</h2>
			<div class="room-top-box">
				<div class="room-top-name" style="display: flex">
					<p style="font-weight: bold;">${host}</p>
					<p>님의 공동구매방</p>
				</div>
				<p style="margin: 0; font-size: 18pt;">${ottGroup.currentMembers}
					/ 4</p>
			</div>
		</div>

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
					<c:choose>
						<c:when test="${ottGroup.isChecked == 1}">
							<p>${ottGroup.ottId}</p>
							<p>${ottGroup.ottPw}</p>
						</c:when>
						<c:otherwise>
							<p>입금 완료 후 표시됩니다.</p>
							<p>입금 완료 후 표시됩니다.</p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>

		<!-- 멤버 상태 -->
		<div class="room-comment"
			style="display: flex; justify-content: center; align-items: center; flex-direction: column; margin-top: 3vh;">
			<p>※ 계좌이체 후, 아래의 입금 완료 버튼을 눌러주세요.</p>
			<p>※ 모든 모임원이 입금 완료 버튼을 누르면, OTT 아이디와 비밀번호를 확인하실 수 있습니다.</p>
		</div>

		<div class="room-bottom">
			<form action="<c:url value='/ottGroup/member/isPaid' />"
				method="post">
				<!-- 그룹 ID와 서비스 ID 전송 -->
				<input type="hidden" name="ottGroupId" value="${ottGroup.groupId}" />
				<input type="hidden" name="serviceId" value="${ottGroup.serviceId }" />
				<!-- 입금 완료 버튼 -->
				<button type="submit" class="payment-button ${isPaid ? 'paid' : ''}"
					${isPaid ? 'disabled' : ''}>입금 완료</button>
			</form>
		</div>

	</main>
</body>
</html>
