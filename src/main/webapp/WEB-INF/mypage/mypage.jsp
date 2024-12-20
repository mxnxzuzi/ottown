<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>마이페이지</title>
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
            display: block;
        }

        .modal-content {
            background-color: #fff;
            margin: 10% auto;
            padding: 20px;
            max-width: 600px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
            text-align: center;
        }

        /* 버튼 스타일 */
        .modal-content button {
            padding: 10px 20px;
            background-color: #fcb900;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin: 10px 5px; /* 버튼 간 간격 */
        }

        .modal-content button:hover {
            background-color: #d6a600;
        }

        .modal-content a {
            text-decoration: none;
        }

        /* 수정 폼 스타일 */
        .update-form {
            display: none;
            margin-top: 20px;
            text-align: left;
        }

        .update-form input {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            border-radius: 5px;
            border: 1px solid #ddd;
        }

        .update-form button {
            background-color: #fcb900;
            color: white;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            border-radius: 5px;
        }

        .update-form button:hover {
            background-color: #d6a600;
        }
    </style>

    <script>
        // 정보 수정 폼 토글 함수
        function toggleUpdateForm() {
            var form = document.getElementById("updateForm");
            form.style.display = (form.style.display === "none" || form.style.display === "") ? "block" : "none";
        }
    </script>
</head>
<body>
<div class="modal">
    <div class="modal-content">
        <h2>마이페이지</h2>
        <p>이름: ${sessionScope['consumerName']}</p>
        <p>이메일: ${sessionScope['consumerEmail']}</p>
        <p>비밀번호: ${sessionScope['consumerPassword']}</p>

        <!-- 회원 정보 수정 버튼 -->
        <button onclick="toggleUpdateForm()">회원 정보 수정</button>

        <!-- 회원 정보 수정 폼 -->
        <div id="updateForm" class="update-form">
            <form action="<c:url value='/consumer/update' />" method="POST">
                <label for="consumerName">이름:</label>
                <input type="text" id="consumerName" name="consumerName" value="${sessionScope['consumerName']}" required>

                <label for="consumerEmail">이메일:</label>
                <input type="email" id="consumerEmail" name="consumerEmail" value="${sessionScope['consumerEmail']}" required>

                <label for="consumerPassword">비밀번호:</label>
                <input type="password" id="consumerPassword" name="consumerPassword" value="${sessionScope['consumerPassword']}" required>

                <button type="submit">수정하기</button>
            </form>
        </div>

        <!-- 버튼 섹션 -->
        <form action="<c:url value='/consumer/delete' />" method="POST" style="display: inline;" onsubmit="return confirm('정말로 탈퇴하시겠습니까?');">
            <button type="submit">회원 탈퇴</button>
        </form>
        <a href="<c:url value='/' />">
            <button>닫기</button>
        </a>
    </div>
</div>
</body>
</html>
