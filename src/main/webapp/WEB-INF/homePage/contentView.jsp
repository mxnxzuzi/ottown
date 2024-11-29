<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String type = request.getParameter("type");
    if (type == null) {
        type = "movie"; // 기본 값 설정
    }

    // 필요한 데이터 처리 로직 추가
    String title;
    switch (type) {
        case "movie":
            title = "영화 페이지";
            break;
        case "drama":
            title = "드라마 페이지";
            break;
        case "animation":
            title = "애니메이션 페이지";
            break;
        default:
            title = "기본 페이지";
            break;
    }
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title><%= title %></title>
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
