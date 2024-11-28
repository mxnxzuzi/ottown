<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>OTTown</title>
<link rel=stylesheet href="<c:url value='/css/fix_style.css' />" type="text/css">
<link rel=stylesheet href="<c:url value='/css/mainpage.css' />" type="text/css">
</head>
<body>
	<!-- 헤더 포함 -->
	<jsp:include page="../common/header.jsp" />
	<main>
		<div class="title">
			<h1>OTTown에서 함께 구매하고 함께 즐기세요.</h1>
			<h3 style="font-weight: normal;">OTT 별 콘텐츠를 확인하고 공동 구매를 시작해보세요!</h3>
		</div>
		<div class="button-container">
			<button class="startButton" type="button" id="startNow">바로
				시작하기</button>
			<button class="checkContentButton" type="button" id="confirmContent">컨텐츠
				확인하기</button>
		</div>
		<div class="conveyor-belt" id="belt">
			<div class="box"><img src="<%= request.getContextPath() %>/images/coupang.webp"></div>
            <div class="box"><img src="<%= request.getContextPath() %>/images/disney.svg"></div>
            <div class="box"><img src="<%= request.getContextPath() %>/images/laftel.webp"></div>
            <div class="box"><img src="<%= request.getContextPath() %>/images/netflix.png"></div>
            <div class="box"><img src="<%= request.getContextPath() %>/images/tving.svg"></div>
            <div class="box"><img src="<%= request.getContextPath() %>/images/wavve.png"></div>
		</div>
	</main>
	<script>
        const button = document.getElementById('startNow');
    
        button.addEventListener('click', function() {
            window.location.href = '<%=request.getContextPath()%>
		/login/login.jsp';
						});
	</script>
</body>
</html>
