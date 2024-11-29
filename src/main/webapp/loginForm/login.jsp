<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>OTTown</title>
  <link rel="stylesheet" href="../css/login_style.css">
</head>
<body>
<div class="title">
  <h1>OTTown</h1>
  <h1>로그인</h1>
</div>
<form action="${pageContext.request.contextPath}/consumer/login" method="POST">
  <div class="form-group">
    <input type="email" id="email" name="email" placeholder="이메일" required>
  </div>
  <div class="form-group">
    <input type="password" id="password" name="password" placeholder="비밀번호" required>
  </div>
  <button type="submit" class="loginButton">로그인</button>
</form>

<!-- 로그인 오류 메시지 표시 -->
<c:if test="${not empty loginError}">
  <p>이메일과 비밀번호를 확인해주세요.</p>
</c:if>

<div class="signup-section">
  <p>계정이 없으신가요? <a href="<c:url value='/signupForm/signup.jsp' />">회원가입</a></p>
</div>

<div style="display: flex; align-items: center; justify-content: center;">
  <hr style="flex: 1; margin: 0 10px;">
  <span style="white-space: nowrap; font-weight: bold;">or</span>
  <hr style="flex: 1; margin: 0 10px;">
</div>

<div class="socialLogin" style="margin-top: 30px;">
  <button type="submit">
    <img src="../images/google.png" alt="Submit">
  </button>
  <button type="submit">
    <img src="../images/kakao.png" alt="Submit">
  </button>
  <button type="submit">
    <img src="../images/naver.png" alt="Submit">
  </button>
</div>

</body>
</html>
