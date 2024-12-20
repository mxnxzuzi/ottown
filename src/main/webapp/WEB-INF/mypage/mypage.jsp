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
            display: block; /* 항상 보이도록 수정 */
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
</head>
<body>
<div class="modal">
    <div class="modal-content">
        <h2>마이페이지</h2>
        <p>이름: ${sessionScope['consumerName']}</p>
        <p>이메일: ${sessionScope['consumerEmail']}</p>
        <p>비밀번호: ${sessionScope['consumerPassword']}</p>

        <a href="<c:url value='/' />"><button>닫기</button></a> <!-- 버튼 클릭 시 메인 페이지로 이동 -->
    </div>
</div>
</body>
</html>
