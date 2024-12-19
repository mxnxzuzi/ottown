package model.dao.consumer;

import model.dao.JDBCUtil;
import model.domain.Consumer;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        // 1. ACCOUNT 테이블에서 이메일과 비밀번호 확인
        String accountSql = "SELECT CONSUMER_ID " +
                "FROM ACCOUNT WHERE CONSUMER_EMAIL = ? AND LOGIN_PASSWORD = ?";

        // 입력된 loginId를 CONSUMER_EMAIL, password를 LOGIN_PASSWORD와 비교
        Object[] accountParam = {loginId, password};
        jdbcUtil.setSqlAndParameters(accountSql, accountParam);

        try (ResultSet rs = jdbcUtil.executeQuery()) {
            if(rs == null) {
                System.out.println("rs is null");
            }
            if (rs.next()) {
                // 로그인 정보가 일치하면 CONSUMER_ID를 가져옴
                long consumerId = rs.getLong("CONSUMER_ID");

                // CONSUMER_ID를 사용해 CONSUMER 정보를 가져옴
                return getConsumerById(consumerId);
            }
            return null; // 일치하는 로그인 정보가 없으면 null 반환
        }
    }

    // CONSUMER_ID를 통해 CONSUMER 정보를 가져오는 메서드
    public Consumer getConsumerById(long consumerId) throws SQLException {
        String consumerSql = "SELECT " +
                "C.CONSUMER_ID, " +
                "C.CONSUMER_NAME, " +
                "C.JOIN_DATE, " +
                "C.UPDATE_DATE, " +
                "C.LOGIN_TYPE, " +
                "A.CONSUMER_EMAIL, " +
                "A.LOGIN_PASSWORD " +
                "FROM CONSUMER C " +
                "JOIN ACCOUNT A ON C.CONSUMER_ID = A.CONSUMER_ID " +
                "WHERE C.CONSUMER_ID = ?";

        Object[] consumerParam = {consumerId};
        jdbcUtil.setSqlAndParameters(consumerSql, consumerParam);

        try (ResultSet rs = jdbcUtil.executeQuery()) {
            if (rs.next()) {
                // Consumer 객체 생성
                Consumer consumer = new Consumer();

                // CONSUMER 정보를 Consumer 객체에 세팅
                consumer.setConsumerId(rs.getLong("CONSUMER_ID"));
                consumer.setConsumerName(rs.getString("CONSUMER_NAME"));
                consumer.setJoinDate(rs.getDate("JOIN_DATE"));
                consumer.setUpdateDate(rs.getDate("UPDATE_DATE"));
                consumer.setLoginType(rs.getInt("LOGIN_TYPE"));

                // ACCOUNT 정보도 추가 세팅
                consumer.setEmail(rs.getString("CONSUMER_EMAIL"));  // 이메일
                consumer.setPassword(rs.getString("LOGIN_PASSWORD"));  // 비밀번호

                // 세팅된 Consumer 객체 반환
                return consumer;
            }
            return null; // 일치하는 CONSUMER 정보가 없으면 null 반환
        }
    }


}