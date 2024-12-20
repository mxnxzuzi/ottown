package controller.notification;

import org.apache.ibatis.annotations.Param;

import model.domain.Notification;

import java.util.List;

public interface NotificationMapper {

    // 1. 29일 경과 알림: 조건에 부합하는 그룹 ID 조회
    List<Integer> selectGroupsFor29DaysNotification();

    // 그룹 방장 ID 조회
    Integer selectGroupLeader(@Param("groupId") int groupId);

    // 2. 모든 멤버 결제 완료 알림: 조건에 부합하는 그룹 ID 조회
    List<Integer> selectGroupsWithAllPaidMembers();
    
    List<Integer> selectGroupMembers(@Param("groupId") int groupId);
    
    List<Notification> getNotificationsByUserId(@Param("userId") long userId);

    // 알림 생성
    void insertNotification(@Param("message") String message, @Param("consumerId") int consumerId);
}
