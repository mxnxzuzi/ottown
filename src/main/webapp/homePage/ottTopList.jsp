<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="util.CrawlingUtil"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>OTTown</title>
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/css/ottTopList.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/css/fix_style.css">
</head>
<body>
	<header>
		<nav class="navbar">
			<div class="logo">
				<a href="#">OTTown</a>
			</div>
			<ul class="nav-content">
				<li><a href="">영화</a></li>
				<li><a href="">드라마</a></li>
				<li><a href="">애니메이션</a></li>
				<li style="margin-top: 3px;"><a href="<%= request.getContextPath() %>/homePage/ottTopList.jsp">OTT</a></li>
			</ul>
			<ul class="nav-user">
				<li><a href="">공동구매</a></li>
				<li><a href="">찜</a></li>
				<li><a href="">마이페이지</a></li>
			</ul>
		</nav>
	</header>
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
