package model.dao.consumer;

import model.dao.JDBCUtil;
import model.domain.Consumer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ConsumerDao {
    private JDBCUtil jdbcUtil = null;

    public ConsumerDao() {
        jdbcUtil = new JDBCUtil(); // JDBCUtil 객체 생성
    }

    // 회원 생성 (회원가입)
    public boolean create(Consumer consumer) throws SQLException {
        String sql = "INSERT INTO CONSUMER (CONSUMER_ID, CONSUMER_NAME, LOGIN_TYPE, UPDATE_DATE, JOIN_DATE) VALUES (SEQ_CONSUMER.NEXTVAL, ?, ?, ?, ?)";
        Object[] param = {
                consumer.getConsumerName(),
                consumer.getLoginType(),
                consumer.getUpdateDate(),
                consumer.getJoinDate()
        };

        try {
            // CONSUMER 삽입 실행
            jdbcUtil.setSqlAndParameters(sql, param);
            int result = jdbcUtil.executeUpdate();

            if (result > 0) {
                // CONSUMER_ID를 CURRVAL로 가져오기
                String consumerIdQuery = "SELECT SEQ_CONSUMER.CURRVAL FROM DUAL";
                jdbcUtil.setSqlAndParameters(consumerIdQuery, null);
                try (ResultSet rs = jdbcUtil.executeQuery()) {
                    if (rs.next()) {
                        consumer.setConsumerId(rs.getLong(1)); // CURRVAL을 consumer에 설정
                    }
                }

                // 로그인 타입이 0이면 Account 테이블에 삽입
                if (consumer.getLoginType() == 0) {
                    String accountSql = "INSERT INTO ACCOUNT (LOGIN_ID, CONSUMER_ID, LOGIN_PASSWORD, CONSUMER_EMAIL) " +
                            "VALUES (?, ?, ?, ?)";
                    Object[] accountParam = {
                            consumer.getConsumerName(),  // LOGIN_ID
                            consumer.getConsumerId(),    // CONSUMER_ID
                            consumer.getPassword(),      // LOGIN_PASSWORD
                            consumer.getEmail()          // CONSUMER_EMAIL
                    };
                    jdbcUtil.setSqlAndParameters(accountSql, accountParam);
                    int accountResult = jdbcUtil.executeUpdate();
                    if (accountResult > 0) {
                        return true; // ACCOUNT INSERT 성공 시 true 반환
                    } else {
                        jdbcUtil.rollback();
                        return false; // ACCOUNT INSERT 실패 시 false 반환
                    }
                }
            }
            return false; // CONSUMER INSERT 실패 시 false 반환
        } catch (SQLException e) {
            jdbcUtil.rollback();
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
    }

    //로그인
    public Consumer findByLoginIdAndPassword(String loginId, String password) throws SQLException {
        // 1. ACCOUNT 테이블에서 로그인 정보 확인
        String accountSql = "SELECT CONSUMER_ID, LOGIN_PASSWORD, CONSUMER_EMAIL " +
                "FROM ACCOUNT WHERE LOGIN_ID = ? AND LOGIN_PASSWORD = ?";

        Object[] accountParam = {loginId, password};
        jdbcUtil.setSqlAndParameters(accountSql, accountParam);

        try (ResultSet rs = jdbcUtil.executeQuery()) {
            if (rs.next()) {
                long consumerId = rs.getLong("CONSUMER_ID");
                // 로그인 정보가 일치하면 CONSUMER_ID를 사용해 CONSUMER 정보를 가져옴
                return getConsumerById(consumerId);
            }
            return null; // 일치하는 로그인 정보가 없으면 null 반환
        }
    }

    // CONSUMER_ID를 통해 CONSUMER 정보를 가져오는 메서드
    private Consumer getConsumerById(long consumerId) throws SQLException {
        String consumerSql = "SELECT CONSUMER_ID, CONSUMER_NAME, LOGIN_TYPE, CONSUMER_EMAIL " +
                "FROM CONSUMER WHERE CONSUMER_ID = ?";

        Object[] consumerParam = {consumerId};
        jdbcUtil.setSqlAndParameters(consumerSql, consumerParam);

        try (ResultSet rs = jdbcUtil.executeQuery()) {
            if (rs.next()) {
                Consumer consumer = new Consumer();
                consumer.setConsumerId(rs.getLong("CONSUMER_ID"));
                consumer.setConsumerName(rs.getString("CONSUMER_NAME"));
                consumer.setLoginType(rs.getInt("LOGIN_TYPE"));
                consumer.setEmail(rs.getString("CONSUMER_EMAIL"));
                return consumer;
            }
            return null; // 일치하는 CONSUMER 정보가 없으면 null 반환
        }
    }




//    // 비밀번호 수정
//    public int updatePassword(Consumer consumer) throws SQLException {
//        String sql = "UPDATE ACCOUNT " +
//                "SET LOGIN_PASSWORD = ? " +
//                "WHERE CONSUMER_ID = ?";
//        Object[] param = new Object[] {
//                consumer.getPassword(),
//                consumer.getConsumerId()
//        };
//        jdbcUtil.setSqlAndParameters(sql, param);
//
//        try {
//            int result = jdbcUtil.executeUpdate(); // UPDATE 문 실행
//            return result;
//        } catch (SQLException e) {
//            jdbcUtil.rollback();
//            throw e; // 예외를 상위 호출자에게 전달
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        } finally {
//            jdbcUtil.commit();
//            jdbcUtil.close(); // 리소스 반환
//        }
//    }
//
//    // 회원 삭제
//    public int remove(String consumerId) throws SQLException {
//        String sql = "DELETE FROM CONSUMER WHERE CONSUMER_ID = ?";
//        jdbcUtil.setSqlAndParameters(sql, new Object[] { consumerId }); // DELETE 문과 매개 변수 설정
//
//        try {
//            int result = jdbcUtil.executeUpdate(); // DELETE 문 실행
//            return result;
//        } catch (Exception ex) {
//            jdbcUtil.rollback();
//            ex.printStackTrace();
//        } finally {
//            jdbcUtil.commit();
//            jdbcUtil.close(); // 리소스 반환
//        }
//        return 0;
//    }
//
//    // 회원 ID로 회원 정보 조회
//    public Consumer findConsumer(String consumerId) throws SQLException {
//        String query = "SELECT * FROM CONSUMER WHERE CONSUMER_ID = ?";
//        jdbcUtil.setSqlAndParameters(query, new Object[] { consumerId });
//
//        try {
//            ResultSet rs = jdbcUtil.executeQuery();
//            while (rs.next()) {
//                Consumer consumer = new Consumer(
//                        rs.getLong("CONSUMER_ID"),  // 오라클 컬럼명에 맞게 수정
//                        rs.getString("EMAIL"),      // 오라클 컬럼명에 맞게 수정
//                        rs.getString("PASSWORD")    // 오라클 컬럼명에 맞게 수정
//                );
//                return consumer;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace(); // 오류 처리
//        } finally {
//            jdbcUtil.close(); // 리소스 반환
//        }
//        return null;
//    }
//
//    // 마이페이지 정보 조회
//    public Consumer findProfile(String consumerId) throws SQLException {
//        String query = "SELECT CONSUMER_NAME, EMAIL, PASSWORD FROM CONSUMER WHERE CONSUMER_ID = ?";
//        jdbcUtil.setSqlAndParameters(query, new Object[] { consumerId });
//
//        try {
//            ResultSet rs = jdbcUtil.executeQuery();
//            if (rs.next()) {
//                // Consumer 객체 생성 및 필드 설정
//                Consumer consumer = new Consumer();
//                consumer.setConsumerName(rs.getString("CONSUMER_NAME")); // 컬럼명 수정
//                consumer.setEmail(rs.getString("EMAIL"));                // 컬럼명 수정
//                consumer.setPassword(rs.getString("PASSWORD"));          // 컬럼명 수정
//
//                return consumer;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace(); // 예외 처리
//        } finally {
//            jdbcUtil.close(); // 리소스 반환
//        }
//        return null; // 정보가 없을 경우 null 반환
//    }
}
