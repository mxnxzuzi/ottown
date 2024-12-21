package model.dao.OTTGroupMember;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.domain.Consumer;
import model.domain.OTTGroup;
import model.domain.OTTGroupMember;
import model.dao.JDBCUtil;

public class OTTGroupMemberDao {

    private JDBCUtil jdbcUtil = null;  // JDBCUtil 필드 선언

    public OTTGroupMemberDao() {
        jdbcUtil = new JDBCUtil();  // JDBCUtil 인스턴스화
    }

 // OTTGroupMember 생성 (OTTGroup에 추가)
    public boolean addOTTGroupMember(OTTGroupMember member) throws Exception {
        String sql = "INSERT INTO OTTGroupMember (isPaid, isRemoved, role, userId, ottGroupId) "
                   + "VALUES (?, ?, ?, ?, ?)";

        jdbcUtil.setSqlAndParameters(
            sql, 
            new Object[] {
                member.isPaid(),
                member.isRemoved(),
                member.isRole(),
                member.getConsumer().getConsumerId(),
                member.getGroup().getId()
            }
        );

        try {
            jdbcUtil.executeUpdate();
            return true; // 성공 시 true 반환
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // 예외 발생 시 false 반환
        } finally {
            jdbcUtil.close();
        }
    }

    
    // 해당 OTTGroup에 속한 멤버 수 카운트
    public int countMembersByOTTGroupId(int ottGroupId) {
        String sql = "SELECT COUNT(*) FROM OTTGroupMember WHERE ottGroupId = ?";

        jdbcUtil.setSqlAndParameters(
            sql, 
            new Object[] { ottGroupId }
        );

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // 멤버 수 반환
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return 0;  // 오류 발생 시 0 반환
    }
    
    // OTTGroup의 특정 member 조회
    public OTTGroupMember getMemberByOTTGroupId(int ottGroupMemberId) {
    	OTTGroupMember member = new OTTGroupMember();
        String sql = "SELECT * FROM OTTGroupMember WHERE ottGroupMemberId = ?";

        jdbcUtil.setSqlAndParameters(
            sql, 
            new Object[] { ottGroupMemberId }
        );

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            member.setId(rs.getInt("id"));
            member.setPaid(rs.getInt("isPaid"));
            member.setRemoved(rs.getInt("isRemoved"));
            member.setRole(rs.getInt("role"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return member;
    }

    // OTTGroup의 OTTGroupMember들을 조회
    public List<OTTGroupMember> getMembersByOTTGroupId(int ottGroupId) {
        List<OTTGroupMember> members = new ArrayList<>();
        String sql = "SELECT * FROM OTTGroupMember WHERE ottGroupId = ?";

        jdbcUtil.setSqlAndParameters(
            sql, 
            new Object[] { ottGroupId }
        );

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            while (rs.next()) {
                OTTGroupMember member = new OTTGroupMember();
                member.setId(rs.getInt("id"));
                member.setPaid(rs.getInt("isPaid"));
                member.setRemoved(rs.getInt("isRemoved"));
                member.setRole(rs.getInt("role"));
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return members;
    }

    // OTTGroupMember 삭제
    public boolean removeOTTGroupMember(int memberId) throws Exception {
        String sql = "DELETE FROM OTTGroupMember WHERE id = ?";

        jdbcUtil.setSqlAndParameters(
            sql, 
            new Object[] { memberId }
        );

        try {
            jdbcUtil.executeUpdate();
            return true; // 성공 시 true 반환
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // 예외 발생 시 false 반환
        } finally {
            jdbcUtil.close();
        }
    }

    
    // host(role=true)의 isChecked=true인지 확인
    public boolean isHostChecked(int ottGroupId) {
        String sql = "SELECT isChecked FROM OTTGroupMember WHERE ottGroupId = ? AND role = true";

        jdbcUtil.setSqlAndParameters(
            sql, 
            new Object[] { ottGroupId }
        );

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("isChecked");  // host의 isChecked 값 반환
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return false;  // 해당 조건에 맞는 호스트가 없으면 false 반환
    }

    // 모든 OTTGroupMember가 isPaid=true인지 확인
    public boolean areAllMembersPaid(int ottGroupId) {
        String sql = "SELECT COUNT(*) FROM OTTGroupMember WHERE ottGroupId = ? AND isPaid = true";

        jdbcUtil.setSqlAndParameters(
            sql, 
            new Object[] { ottGroupId }
        );

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                int paidCount = rs.getInt(1);
                // 그룹 내 4명이 모두 입금했을 경우 true 반환
                return paidCount == 4;  // 모든 멤버가 입금을 완료했는지 확인
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return false;
    }
    
    // 입금 상태 업데이트
    public boolean updatePaidStatus(int ottGroupMemberId, int ottGroupId) throws Exception {
        String sql = "UPDATE ott_group_member " 
                   + "SET paid = TRUE " 
                   + "WHERE ott_group_member_id = ? AND ott_group_id = ?";

        // SQL과 매개변수 설정
        jdbcUtil.setSqlAndParameters(
            sql, 
            new Object[] {ottGroupMemberId, ottGroupId}
        );

        try {
            // 업데이트 실행
            int rowsAffected = jdbcUtil.executeUpdate(); // 영향받은 행의 수
            return rowsAffected > 0; // 1개 이상 업데이트된 경우 true 반환
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // 예외 발생 시 false 반환
        } finally {
            jdbcUtil.close(); // 리소스 정리
        }
    }


    // 특정 OTTGroup에 특정 멤버가 존재하는지 확인
    public boolean isMemberInGroup(int groupId, int memberId) {
        String sql = "SELECT COUNT(*) FROM OTTGroupMember WHERE ottGroupId = ? AND id = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] { groupId, memberId });

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // 멤버가 존재하면 true 반환
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return false;
    }
    
    // memberId를 기준으로 Consumer 정보 가져오기
    public Consumer getConsumerByMemberId(int memberId) throws SQLException {
        // 1. OTTGroupMember에서 해당 memberId로 ConsumerId 찾기
        String sql = "SELECT CONSUMER_ID FROM OTTGroupMember WHERE id = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[]{memberId});

        try (ResultSet rs = jdbcUtil.executeQuery()) {
            if (rs.next()) {
                long consumerId = rs.getLong("CONSUMER_ID"); // memberId로 얻은 CONSUMER_ID

                // 2. 얻은 CONSUMER_ID로 Consumer 정보 가져오기
                return getConsumerById(consumerId);
            }
            return null; // 해당 memberId에 해당하는 Consumer가 없으면 null 반환
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            jdbcUtil.close();  // JDBC 리소스 정리
        }
    }

    // CONSUMER_ID를 통해 CONSUMER 정보를 가져오는 메서드
    public Consumer getConsumerById(long consumerId) throws SQLException {
        String sql = "SELECT CONSUMER_ID, CONSUMER_NAME, JOIN_DATE, UPDATE_DATE, LOGIN_TYPE, CONSUMER_EMAIL " +
                     "FROM CONSUMER C JOIN ACCOUNT A ON C.CONSUMER_ID = A.CONSUMER_ID WHERE C.CONSUMER_ID = ?";

        jdbcUtil.setSqlAndParameters(sql, new Object[]{consumerId});

        try (ResultSet rs = jdbcUtil.executeQuery()) {
            if (rs.next()) {
                // Consumer 객체 생성
                Consumer consumer = new Consumer();
                consumer.setConsumerId(rs.getLong("CONSUMER_ID"));
                consumer.setConsumerName(rs.getString("CONSUMER_NAME"));
                consumer.setJoinDate(rs.getDate("JOIN_DATE"));
                consumer.setUpdateDate(rs.getDate("UPDATE_DATE"));
                consumer.setLoginType(rs.getInt("LOGIN_TYPE"));
                consumer.setEmail(rs.getString("CONSUMER_EMAIL"));
                return consumer;
            }
            return null; // 일치하는 Consumer 정보가 없으면 null 반환
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            jdbcUtil.close();  // JDBC 리소스 정리
        }
    }
    
    // role이 true인 멤버의 consumerId를 찾는 메서드
    public Long getConsumerIdByRoleTrue(int ottGroupMemberId) throws SQLException {
        // 1. OTTGroupMember에서 해당 ottGroupMemberId로 member 정보를 가져옴
        String sql = "SELECT CONSUMER_ID FROM OTTGroupMember WHERE id = ? AND role = true";

        jdbcUtil.setSqlAndParameters(sql, new Object[]{ottGroupMemberId});

        try (ResultSet rs = jdbcUtil.executeQuery()) {
            if (rs.next()) {
                // role이 true인 멤버가 있으면 consumerId를 반환
                return rs.getLong("CONSUMER_ID");
            }
            return null; // role이 true인 멤버가 없으면 null 반환
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            jdbcUtil.close();  // JDBC 리소스 정리
        }
    }
    
    public OTTGroupMember findMemberByConsumerId(int consumerId) throws SQLException {
        String sql = "SELECT * FROM OTTGroupMember WHERE consumerId = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] { consumerId });

        try (ResultSet rs = jdbcUtil.executeQuery()) {
            if (rs.next()) {
                OTTGroupMember member = new OTTGroupMember();
                member.setId(rs.getInt("id"));
                member.setPaid(rs.getInt("isPaid"));
                member.setRemoved(rs.getInt("isRemoved"));
                member.setRole(rs.getInt("role"));
                member.setGroup(new OTTGroup(rs.getInt("ottGroupId")));
                return member;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

}