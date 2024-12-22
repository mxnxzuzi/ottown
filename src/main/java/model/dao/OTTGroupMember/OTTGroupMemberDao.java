package model.dao.OTTGroupMember;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dao.JDBCUtil;
import model.domain.OTTGroupMember;

public class OTTGroupMemberDao {

    private JDBCUtil jdbcUtil = null;  // JDBCUtil 필드 선언

    public OTTGroupMemberDao() {
        jdbcUtil = new JDBCUtil();  // JDBCUtil 인스턴스화
    }

    // OTTGroupMember 생성
    public int addOTTGroupMember(long groupId, long consumerId) {
        String sql = "INSERT INTO GROUP_MEMBER (ROLE, IS_PAID, IS_REMOVED, CONSUMER_ID, GROUP_ID) "
                   + "VALUES (0, 0, 0, ?, ?)";
        jdbcUtil.setSqlAndParameters(sql, new Object[]{consumerId, groupId});

        try {
            int result = jdbcUtil.executeUpdate();
            jdbcUtil.commit();
            return result;
        } catch (Exception e) {
            jdbcUtil.rollback();
        } finally {
            jdbcUtil.close();
        }
        
        return 0;
    }

    // ottGroup의 한 member 조회
    public OTTGroupMember getMemberById(long groupId, long consumerId) {
        String sql = "SELECT * FROM GROUP_MEMBER WHERE GROUP_ID = ? AND CONSUMER_ID = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[]{groupId, consumerId});

        try {
           ResultSet rs = jdbcUtil.executeQuery();
           OTTGroupMember member = new OTTGroupMember();
           
            if (rs.next()) {
                member.setIsPaid(rs.getInt("is_paid"));
                member.setIsRemoved(rs.getInt("is_removed"));
                member.setRole(rs.getInt("role"));
                member.setConsumerId(rs.getLong("consumer_id"));
                member.setGroupId(rs.getLong("group_id"));
            }
            
            return member;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    // OTTGroup의 member들 조회
    public List<OTTGroupMember> getMembersByGroupId(long groupId) {
        String sql = "SELECT * FROM GROUP_MEMBER WHERE GROUP_ID = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[]{groupId});

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            List<OTTGroupMember> members = new ArrayList<>();

            while (rs.next()) {
                OTTGroupMember member = new OTTGroupMember();
                member.setIsPaid(rs.getInt("is_paid"));
                member.setIsRemoved(rs.getInt("is_removed"));
                member.setRole(rs.getInt("role"));
                member.setConsumerId(rs.getLong("consumer_id"));
                member.setGroupId(rs.getLong("group_id"));
                members.add(member); // 멤버 리스트에 추가
            }
            
            return members;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
    
    // host 조회
    public OTTGroupMember getHost(long groupId) {
       String sql = "SELECT * FROM GROUP_MEMBER WHERE GROUP_ID = ? AND ROLE = 1";
       jdbcUtil.setSqlAndParameters(sql, new Object[] {groupId});
       
       try {
          ResultSet rs = jdbcUtil.executeQuery();
          OTTGroupMember member = new OTTGroupMember();
          
          if(rs.next()) {
                member.setIsPaid(rs.getInt("is_paid"));
                member.setIsRemoved(rs.getInt("is_removed"));
                member.setRole(rs.getInt("role"));
                member.setConsumerId(rs.getLong("consumer_id"));
                member.setGroupId(rs.getLong("group_id"));
          }
          
          return member;
       } catch (Exception e){
          e.printStackTrace();
       } finally {
          jdbcUtil.close();
       }
       
       return null;
    }

    // Member 삭제
    public int removeOTTGroupMember(long groupId, long consumerId) {
        String sql = "DELETE FROM GROUP_MEMBER WHERE GROUP_ID = ? AND CONSUMER_ID = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[]{groupId, consumerId});

        try {
            int result = jdbcUtil.executeUpdate();
            jdbcUtil.commit();
            return result;
        } catch (Exception e) {
            jdbcUtil.rollback();
        } finally {
            jdbcUtil.close();
        }
        
        return 0;
    }


    // 모든 member가 isPaid=1인지 확인
    public boolean areAllMembersPaid(long groupId) {
        String sql = "SELECT COUNT(*) FROM GROUP_MEMBER WHERE GROUP_ID = ? AND IS_PAID = 1";
        jdbcUtil.setSqlAndParameters(sql, new Object[] {groupId});

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            int paidCount;
            
            if (rs.next()) {
                paidCount = rs.getInt(1);
                return paidCount == 4;  // 모든 멤버가 입금을 완료했는지 확인
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return false;
    }
    
    // 호스트 role 업데이트
    public int updateRole(long groupId, long consumerId) {
        String sql = "UPDATE GROUP_MEMBER SET role = 1, IS_PAID = 1 WHERE CONSUMER_ID = ? AND GROUP_ID = ?";
         jdbcUtil.setSqlAndParameters(sql, new Object[] { consumerId, groupId });

         try {
             int result = jdbcUtil.executeUpdate();
             jdbcUtil.commit();
             return result;
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             jdbcUtil.close();
         }
         
         return 0;
    }
    
    // 멤버 isPaid 업데이트
    public int updateIsPaid(long groupId, long consumerId) {
       String sql = "UPDATE GROUP_MEMBER SET IS_PAID = 1 WHERE GROUP_ID = ? AND CONSUMER_ID = ?";
       jdbcUtil.setSqlAndParameters(sql, new Object[] {groupId, consumerId});
       
       try {
          int result = jdbcUtil.executeUpdate();
          jdbcUtil.commit();
          return result;
       } catch(Exception e) {
          
       } finally {
          jdbcUtil.close();
       }
       
       return 0;
    }
    
}
