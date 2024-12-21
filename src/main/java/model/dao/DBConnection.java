package model.dao;

import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnection {
    // JNDI를 통해 Connection 객체를 반환
    public static Connection getConnection() throws Exception {
        try {
            // Initial Context 생성
            Context initContext = new InitialContext();
            // JNDI 경로 탐색
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            // DataSource 찾기
            DataSource ds = (DataSource) envContext.lookup("jdbc/OracleDS");
            // Connection 반환
            return ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Oracle 데이터베이스 연결 실패: " + e.getMessage(), e);
        }
    }

    // 테스트 메서드
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Oracle 데이터베이스 연결 성공!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Oracle 데이터베이스 연결 실패: " + e.getMessage());
        }
    }
}
