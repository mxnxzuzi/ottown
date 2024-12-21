package model.service;

import model.dao.consumer.ConsumerDao;
import model.domain.Consumer;

import java.sql.SQLException;

public class ConsumerManager {

    private ConsumerDao consumerDao;

    public ConsumerManager() {
        consumerDao = new ConsumerDao();
    }

    // 회원 가입
    public boolean registerConsumer(Consumer consumer) {
        try {
            return consumerDao.create(consumer);
        } catch (SQLException e) {
            System.err.println("회원 가입 중 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
    }

    // 로그인
    public Consumer authenticateConsumer(String email, String password) {
        try {
            return consumerDao.findByLoginIdAndPassword(email, password);
        } catch (SQLException e) {
            System.err.println("로그인 중 오류가 발생했습니다: " + e.getMessage());
            return null;
        }
    }

    // 회원 삭제
    public boolean deleteConsumer(long consumerId) {
        try {
            // ConsumerDao의 delete 메서드를 호출하여 회원 삭제
            return consumerDao.deleteConsumerById(consumerId);
        } catch (SQLException e) {
            // SQLException 발생 시 로그를 찍고, 실패 처리
            System.err.println("회원 삭제 중 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
    }

    // 회원 정보 수정
    public boolean updateConsumerInfo(long consumerId, String newName, String newEmail, String newPassword) {
        try {
            // ConsumerDao의 updateAccount 메서드를 호출하여 ACCOUNT 테이블 수정
            // LOGIN_ID 자리에 newName (사용자 이름)을 넣고, EMAIL 자리에 newEmail을 넣습니다.
            boolean isAccountUpdated = consumerDao.updateAccountInfo(
                    consumerId,  // consumerId
                    newName,     // LOGIN_ID (사용자 이름)
                    newPassword, // LOGIN_PASSWORD (비밀번호)
                    newEmail     // CONSUMER_EMAIL (이메일)
            );

            if (isAccountUpdated) {
                // ACCOUNT 수정이 성공하면 CONSUMER 테이블 수정
                return consumerDao.updateConsumerName(consumerId, newName);
            }
            return false; // ACCOUNT 업데이트 실패 시
        } catch (SQLException e) {
            // SQLException 발생 시 로그를 찍고, 실패 처리
            System.err.println("회원 정보 수정 중 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
    }




}
