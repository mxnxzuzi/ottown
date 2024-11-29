<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="util.CrawlingUtil"%>


<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>OTTown</title>
<link rel=stylesheet href="<c:url value='/css/ottTopList.css' />"
	type="text/css">
<link rel=stylesheet href="<c:url value='/css/base.css' />"
	type="text/css">
</head>
<body>
	<!-- 헤더 포함 -->
	<jsp:include page="../common/header.jsp" />
	<main>
		<div class="ott-container">
			<h1>Netflix Top10</h1>
			<div class="content-container">
				<%= CrawlingUtil.crawlOTTData("netflix") %>
			</div>
			<h1>Tving Top10</h1>
			<div class="content-container">
				<%= CrawlingUtil.crawlOTTData("tving") %>
			</div>
			<h1>Disney Top10</h1>
			<div class="content-container">
				<%= CrawlingUtil.crawlOTTData("disney") %>
			</div>
			<h1>Coupang Play Top10</h1>
			<div class="content-container">
				<%= CrawlingUtil.crawlOTTData("coupang") %>
			</div>
			<h1>Wavve Top10</h1>
			<div class="content-container">
				<%= CrawlingUtil.crawlOTTData("wavve") %>
			</div>
			<h1>Watcha Top10</h1>
			<div class="content-container">
				<%= CrawlingUtil.crawlOTTData("watcha") %>
			</div>
		</div>
	</main>
</body>
</html>