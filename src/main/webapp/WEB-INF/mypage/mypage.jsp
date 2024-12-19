<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .mypage-info p {
            margin: 5px 0;
        }
    </style>
</head>
<body>
<h2>My Page</h2>
<p>이름: ${consumerName}</p>
<p>이메일: ${consumerEmail}</p>
<p>비밀번호: ${password}</p>
</body>
</html>
