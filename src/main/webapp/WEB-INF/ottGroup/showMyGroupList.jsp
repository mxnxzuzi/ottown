<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>나의 공동구매방</title>
    <link rel="stylesheet" href="<c:url value='/css/base.css' />" type="text/css">
    <link rel="stylesheet" href="<c:url value='/css/myGroupList.css' />" type="text/css">
</head>
<body>
	<jsp:include page="../common/header.jsp" />
	
	<main>
	    <section class="group-list-container">
	        <h1>나의 공동구매방</h1>
	        <div class="group-list">
	            <c:forEach var="group" items="${ottGroupList}">
	                <div class="group-item">
	                	<a href="<c:url value='mypage/ottGroup/view/group?groupId=${group.groupId}&serviceId=${group.serviceId }' />">
		                    <div class="group-info">
		                        <span class="host-name">${hostNames[group.groupId]} 님의 공동구매방</span>
		                        <span class="participant-count">${group.currentMembers} / 4</span>
		                    </div>
	                    </a>
	                </div>
	            </c:forEach>
	        </div>
	    </section>
	</main>

</body>
</html>
