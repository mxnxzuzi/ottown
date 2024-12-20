<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<header>
	<nav class="navbar">
		<div class="logo">
			<a href="<c:url value='/' />">OTTown</a>
		</div>
		<ul class="nav-content">
			<li><a href="<c:url value='/content/view?type=movie' />">영화</a></li>
			<li><a href="<c:url value='/content/view?type=drama' />">드라마</a></li>
			<li><a href="<c:url value='/content/view?type=animation' />">애니메이션</a></li>
			<li style="margin-top: 3px;"><a href="<c:url value='/content/top10' />">OTT</a></li>
		</ul>
		<ul class="nav-user">
			<!-- 로그인 상태일 때 보이는 항목 -->
			<c:if test="${not empty sessionScope['userSessionKey']}">
				<li><a href="<c:url value='/room/list' />">공동구매</a></li>
				<li><a href="<c:url value='/storage/view' />">찜</a></li>
				<li><a href="<c:url value='/consumer/mypage' />">마이페이지</a></li> <!-- 마이페이지 링크로 연결 -->
				<li><a href="<c:url value='/consumer/logout' />">로그아웃</a></li>
			</c:if>

			<!-- 로그아웃 상태일 때 보이는 항목 -->
			<c:if test="${empty sessionScope['userSessionKey']}">
				<li><a href="<c:url value='/consumer/login' />">로그인</a></li>
				<li><a href="<c:url value='/consumer/signup' />">회원가입</a></li>
			</c:if>
		</ul>
	</nav>
</header>
