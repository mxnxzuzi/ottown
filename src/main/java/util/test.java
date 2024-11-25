package util;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.dao.JDBCUtil;

public class test {
    private JDBCUtil jdbcUtil = null;

    public test() {
        jdbcUtil = new JDBCUtil(); // JDBCUtil 객체 생성
    }

    public int getServiceIdByName(String serviceName) {
        int serviceId = -1;
        String query = "SELECT SERVICE_ID FROM OTTSERVICE WHERE LOWER(SERVICE_NAME) = LOWER(?)";
        Object[] param = new Object[] { serviceName.trim() };

        jdbcUtil.setSqlAndParameters(query, param);

        try {
            ResultSet resultSet = jdbcUtil.executeQuery();

            if (resultSet.next()) {
                serviceId = resultSet.getInt("SERVICE_ID");
            }
        } catch (SQLException e) {
            jdbcUtil.rollback();
            e.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }

        return serviceId;
    }

    public static void main(String[] args) {
        test dao = new test(); // 인스턴스 생성
        String serviceName = "netflix";
        int serviceId = dao.getServiceIdByName(serviceName); // 인스턴스 메서드 호출
        System.out.println("Service ID for " + serviceName + ": " + serviceId);
    }
}
