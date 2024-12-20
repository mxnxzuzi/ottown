<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>알림</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            min-height: 100vh;
        }

        .notification-container {
            background-color: #ffffff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            padding: 20px;
            width: 70%;
        }

        .notification-container h1 {
            text-align: center;
            font-size: 24px;
            color: #333;
            margin-bottom: 20px;
        }

        .notification-list {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .notification-list li {
            background-color: #f9f9f9;
            border: 1px solid #e0e0e0;
            border-radius: 5px;
            padding: 20px 20px;
            margin-bottom: 2vh;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .notification-list li span {
            font-size: 16px;
            color: #555;
        }

        .notification-list li strong {
            font-size: 14px;
            color: #d9534f; /* 빨간색으로 강조 */
            margin-left: 10px;
        }

        .no-notifications {
            text-align: center;
            font-size: 16px;
            color: #888;
        }
    </style>
</head>
<body>
    <div class="notification-container">
        <h1>알림</h1>
        <c:if test="${not empty notifications}">
            <ul class="notification-list">
                <c:forEach var="notification" items="${notifications}">
                    <li>
                        <span>${notification.message}</span>
                        <c:if test="${!notification.isChecked}">
                            <strong>(새 알림)</strong>
                        </c:if>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${empty notifications}">
            <p class="no-notifications">알림이 없습니다.</p>
        </c:if>
    </div>
</body>
</html>
