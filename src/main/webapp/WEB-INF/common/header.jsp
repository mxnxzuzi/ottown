<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="controller.consumer.UserSessionUtils" %>

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
<%--				<p>로그인한 사용자: ${sessionScope['userSessionKey']}</p>--%>
				<li><a href="<c:url value='/room/list' />">공동구매</a></li>
				<li><a href="<c:url value='/storage/view' />">찜</a></li>
				<li><a href="#" onclick="openModal()">마이페이지</a></li> <!-- 마이페이지 클릭 시 모달 열기 -->
				<li><a href="<c:url value='/consumer/logout' />">로그아웃</a></li>
			</c:if>

			<!-- 로그아웃 상태일 때 보이는 항목 -->
			<c:if test="${empty sessionScope['userSessionKey']}">
<%--				<p>로그인하지 않은 상태입니다.</p>--%>
				<li><a href="<c:url value='/consumer/login' />">로그인</a></li>
				<li><a href="<c:url value='/consumer/signup' />">회원가입</a></li>
			</c:if>
		</ul>
	</nav>
</header>

<!-- 마이페이지 모달 -->
<div id="mypage-modal" class="modal">
	<div class="modal-content">
		<h2>마이페이지</h2>
		<p>이름: ${sessionScope['consumerName']}</p>
		<p>이메일: ${sessionScope['consumerEmail']}</p>
		<p>비밀번호: ${sessionScope['consumerPassword']}</p>

		<button onclick="closeModal()">닫기</button>
	</div>
</div>


<script>
	// 모달 열기 함수
	function openModal() {
		document.getElementById("mypage-modal").style.display = "block";
		document.body.style.overflow = "hidden"; // 모달 열 때 스크롤 비활성화
	}

	// 모달 닫기 함수
	function closeModal() {
		document.getElementById("mypage-modal").style.display = "none";
		document.body.style.overflow = "auto"; // 모달 닫을 때 스크롤 활성화
	}

	// 모달 외부 클릭 시 닫기
	window.onclick = function(event) {
		if (event.target == document.getElementById("mypage-modal")) {
			closeModal();
		}
	}
</script>

<style>
	/* 모달 스타일 */
	.modal {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background: rgba(0, 0, 0, 0.5);
		z-index: 1000;
		display: none; /* 처음에는 숨김 처리 */
	}

	.modal-content {
		background-color: #fff;
		margin: 10% auto;
		padding: 20px;
		max-width: 600px;
		border-radius: 10px;
		box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
	}

	/* Close button 스타일 */
	.modal-content button {
		padding: 10px 20px;
		background-color: #fcb900;
		color: #fff;
		border: none;
		border-radius: 5px;
		cursor: pointer;
	}

	.modal-content button:hover {
		background-color: #d6a600;
	}
</style>

