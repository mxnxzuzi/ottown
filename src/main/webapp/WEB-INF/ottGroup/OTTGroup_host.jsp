<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.dao.consumer.ConsumerDao"%>
<%@ page import="model.domain.OTTGroupMember"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>OTTown</title>
    <link rel="stylesheet" href="<c:url value='/css/base.css' />" type="text/css">
    <link rel="stylesheet" href="<c:url value='/css/ottGroup_host.css' />" type="text/css">
</head>
<body>
    <!-- 헤더 포함 -->
    <jsp:include page="../common/header.jsp" />

    <main>
        <!-- 상단 정보 -->
        <div class="room-top">
            <%-- <img src="<c:url value='${ottService.image}'  />" alt="${ottService.name} Logo"> --%>
            <h2>${ottService.name}</h2>
            <div class="room-top-box">
                <div class="room-top-name">
                    <p class="room-host-name">${host}</p>
                    <p>님의 공동구매방</p>
                </div>
                <p class="room-count">${ottGroup.currentMembers} / 4</p>
            </div>
        </div>

        <!-- 방 정보 -->
        <div class="room-middle">
            <div class="room-middle-box">
                <div class="room-inform">
                    <p>계좌번호</p>
                    <p>${ottGroup.account}</p>
                </div>
                <div class="room-inform">
                    <p>카톡아이디</p>
                    <p>${ottGroup.kakaoId}</p>
                </div>
                <div class="room-inform">
                    <p>OTT 아이디</p>
                    <p>${ottGroup.ottId}</p>
                </div>
                <div class="room-inform">
                    <p>OTT 비밀번호</p>
                    <p>${ottGroup.ottPw}</p>
                </div>
            </div>
        </div>

		<%
			ConsumerDao consumerDao = new ConsumerDao();
		%>
        <!-- 멤버 목록 -->
        <div class="room-members">
            <c:forEach var="member" items="${ottGroupMembers}">
                <div class="member-row">
                	<% 
		                OTTGroupMember currentMember = (OTTGroupMember) pageContext.getAttribute("member");
		                String consumerName = consumerDao.getConsumerById(currentMember.getConsumerId()).getConsumerName();
		            %>
                    <p class="member-name"><%= consumerName %></p>
                    <!-- 입금완료 상태 표시 -->
                     <c:choose>
                         <c:when test="${member.isPaid == 1}">
                             <div class="payment-status paid">입금완료</div>
                         </c:when>
                         <c:otherwise>
                             <div class="payment-status hidden"></div>
                         </c:otherwise>
                     </c:choose>
         
                     <!-- 삭제 버튼 -->
                     <form action="<c:url value='/ottGroup/removeMember' />" method="post">
                         <input type="hidden" name="consumerId" value="${member.consumerId}" />
                         <input type="hidden" name="groupId" value="${member.groupId}" />
                         <button type="submit" class="remove-button">-</button>
                     </form>
                </div>
            </c:forEach>
        </div>

        <!-- 계정 공유 버튼 -->
        <div class="room-bottom">
            <c:choose>
                <c:when test="${ottGroup.isChecked == 1}">
                    <button class="account-share shared" disabled>계정 공유</button>
                </c:when>
                <c:otherwise>
                    <form action="<c:url value='/ottGroup/share' />" method="post">
                        <input type="hidden" name="groupId" value="${ottGroup.groupId}" />
                        <button type="submit" class="account-share active">계정 공유</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </main>
</body>
</html>
