package util;

import controller.notification.NotificationController;

public class NotificationTest {

    public static void main(String[] args) {
        try {
            // MyBatis 세션 팩토리로 NotificationController 초기화
            NotificationController notificationController = new NotificationController(MyBatisUtils.getSqlSessionFactory());

            // 알림 전송 테스트 실행
            System.out.println("=== 알림 테스트 시작 ===");
            notificationController.sendNotifications();
            System.out.println("=== 알림 테스트 완료 ===");
        } catch (Exception e) {
            System.err.println("알림 테스트 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}