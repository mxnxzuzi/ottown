<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header>
<!-- 수정된 url 참고해서 다시 수정하기 -->
    <nav class="navbar">
        <div class="logo">
            <a href="/home">OTTown</a>
        </div>
        <ul class="nav-content">
            <li><a href="/movie/list">영화</a></li>
            <li><a href="/drama/list">드라마</a></li>
            <li><a href="/animation/list">애니메이션</a></li>
            <li style="margin-top: 3px;"><a href="/ott/list">OTT</a></li>
        </ul>
        <ul class="nav-user">
            <li><a href="/room/list">공동구매</a></li>
            <li><a href="/storage/view">찜</a></li>
            <li><a href="/user/mypage">마이페이지</a></li>
        </ul>
    </nav>
</header>
