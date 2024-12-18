<%--
  Created by IntelliJ IDEA.
  User: kirby
  Date: 24. 12. 18.
  Time: 오후 6:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        .mypage-container {
            max-width: 400px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            background-color: #f9f9f9;
        }

        .mypage-container h2 {
            text-align: center;
            color: #333;
        }

        .mypage-info {
            margin-top: 10px;
            line-height: 1.6;
        }

        .mypage-info p {
            margin: 5px 0;
        }

        .button-container {
            text-align: center;
            margin-top: 20px;
        }

        .button-container button {
            padding: 10px 20px;
            font-size: 16px;
            color: #fff;
            background-color: #007bff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .button-container button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="mypage-container">
    <h2>My Page</h2>
    <div class="mypage-info">
        <p>${consumerName}</p>
        <p>${consumerEmail}</p>
        <p>${password}</p>
    </div>
    <div class="button-container">
        <button onclick="closeModal()">Close</button>
    </div>
</div>

<script>
    // 모달 닫기 함수
    function closeModal() {
        // 부모 페이지에서 모달을 닫는 로직 추가
        window.parent.document.getElementById("mypage-modal").classList.add("hidden");
    }
</script>
</body>
</html>

