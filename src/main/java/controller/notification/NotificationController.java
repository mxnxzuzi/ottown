package controller.notification;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    private final SqlSessionFactory sqlSessionFactory;

    public NotificationController(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void sendNotifications() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            NotificationMapper mapper = session.getMapper(NotificationMapper.class);

            // 1. 29일 경과 알림
            List<Integer> groupsFor29Days = mapper.selectGroupsFor29DaysNotification();
            System.out.println("29일 경과 그룹 ID 목록: " + groupsFor29Days);

            for (Integer groupId : groupsFor29Days) {
                // 해당 그룹의 모든 멤버 ID 가져오기
                List<Integer> memberIds = mapper.selectGroupMembers(groupId);
                System.out.println("그룹 ID " + groupId + "의 멤버 ID 목록: " + memberIds);

                // 모든 멤버에게 알림 전송
                for (Integer memberId : memberIds) {
                    String message = "그룹 구독이 29일 경과했습니다. 내일 방이 삭제됩니다.";
                    mapper.insertNotification(message, memberId);
                    System.out.println("29일 경과 알림 생성: " + message + " -> 멤버 ID: " + memberId);
                }
            }

            // 2. 모든 멤버 결제 완료 알림
            List<Integer> groupsWithAllPaid = mapper.selectGroupsWithAllPaidMembers();
            System.out.println("모든 멤버 결제 완료 그룹 ID 목록: " + groupsWithAllPaid);

            for (Integer groupId : groupsWithAllPaid) {
                Integer leaderId = mapper.selectGroupLeader(groupId);
                System.out.println("모든 멤버 결제 완료 그룹 ID: " + groupId + ", 방장 ID: " + leaderId);
                if (leaderId != null) {
                    String message = "모든 구성원이 결제를 완료했습니다. 아이디와 비밀번호를 공유해주세요.";
                    mapper.insertNotification(message, leaderId);
                    System.out.println("모든 멤버 결제 완료 알림 생성: " + message);
                }
            }

            session.commit();
            System.out.println("트랜잭션 커밋 완료");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("알림 처리 중 오류 발생: " + e.getMessage());
        }
    }
}
