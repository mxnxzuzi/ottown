<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>드라마 리스트</title>
<link rel=stylesheet href="<c:url value='/css/fix_style.css' />"
	type="text/css">
<link rel="stylesheet" href="<c:url value='/css/movieView.css' />"
	type="text/css" />
</head>
<body>
	<!-- 헤더 포함 -->
	<jsp:include page="../common/header.jsp" />

	<div class="movie-list-container">
		<div class="movie-grid">
			<c:forEach var="movie" items="${contents}">
				<div class="movie-card">
					<img src="${movie.image}" alt="${movie.title}" />
					<button class="like-button">♥</button>
					<div class="movie-info">
						<p class="title">${movie.title}</p>
						<p class="platforms">
							<c:forEach var="platform" items="${movie.ottServices}">
								<span>${platform}</span>
							</c:forEach>
						</p>
					</div>
				</div>

			</c:forEach>
		</div>
	</div>

</body>
</html>
