package model.dao.OTTGroup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.domain.OTTGroup;
import model.domain.OTTService;
import model.dao.JDBCUtil;

public class OTTGroupDao {
    private JDBCUtil jdbcUtil = new JDBCUtil();

    public OTTGroupDao() {
        // 기본 생성자
    }

    // OTTGroup 생성
    public long createOTTGroup(OTTGroup ottGroup) {
       String seqSql = "SELECT SEQ_GROUP.NEXTVAL FROM DUAL";
        String insertSql = "INSERT INTO OTT_GROUP (GROUP_ID, PERIOD, STATUS, CURRENT_MEMBERS, IS_CHECKED, " +
                           "KAKAO_ID_INFO, ACCOUNT_INFO, OTT_ID_INFO, OTT_PW_INFO, SERVICE_ID) " +
                           "VALUES (?, SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
           // SEQ_GROUP에서 NEXTVAL 가져오기
            jdbcUtil.setSqlAndParameters(seqSql, null);
            ResultSet rs = jdbcUtil.executeQuery();

            // ResultSet에서 값 가져오기
            long groupId = 0;
            if (rs.next()) {
                groupId = rs.getLong(1); // 첫 번째 컬럼 값 가져오기
            } else {
                throw new RuntimeException("Failed to generate group ID using SEQ_GROUP.NEXTVAL");
            }

            // INSERT 실행
            jdbcUtil.setSqlAndParameters(insertSql, new Object[] {
                groupId,
                1,
                1,
                0,
                ottGroup.getKakaoId(),
                ottGroup.getAccount(),
                ottGroup.getOttId(),
                ottGroup.getOttPw(),
                ottGroup.getServiceId()
            });

            jdbcUtil.executeUpdate();
            jdbcUtil.commit();
            
            return groupId;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        
        return 0;
    }

    // OTTGroup ID로 특정 OTTGroup 조회
    public OTTGroup getOTTGroupById(long groupId) {
        String sql = "SELECT period, status, current_members, is_checked, kakao_id_info, account_info, ott_id_info, ott_pw_info, service_id "
              + "FROM OTT_GROUP WHERE GROUP_ID = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] { groupId });

        try {
           ResultSet rs = jdbcUtil.executeQuery();
           OTTGroup group = new OTTGroup();
           
           if (rs.next()) {
                group.setGroupId(groupId);
                group.setPeriod(rs.getDate("period"));
                group.setStatus(rs.getInt("status"));
                group.setCurrentMembers(rs.getInt("current_members"));
                group.setIsChecked(rs.getInt("is_checked"));
                group.setKakaoId(rs.getString("kakao_id_info"));
                group.setAccount(rs.getString("account_info"));
                group.setOttId(rs.getString("ott_id_info"));
                group.setOttPw(rs.getString("ott_pw_info"));
                group.setServiceId(rs.getInt("service_id"));
            }
            return group;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    // 특정 OTT 서비스에 해당하는 OTTGroup 목록 조회
    public List<OTTGroup> getOTTGroupsByServiceId(int serviceId) {
        String sql = "SELECT group_id, period, status, current_members, is_checked, kakao_id_info, account_info, ott_id_info, ott_pw_info, service_id "
              + "FROM OTT_GROUP WHERE SERVICE_ID = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] { serviceId });

        try {
           ResultSet rs = jdbcUtil.executeQuery();
           List<OTTGroup> groupList = new ArrayList<>();
            while (rs.next()) {
               OTTGroup group = new OTTGroup();
               
               group.setGroupId(rs.getLong("group_id"));
                group.setPeriod(rs.getDate("period"));
                group.setStatus(rs.getInt("status"));
                group.setCurrentMembers(rs.getInt("current_members"));
                group.setIsChecked(rs.getInt("is_checked"));
                group.setKakaoId(rs.getString("kakao_id_info"));
                group.setAccount(rs.getString("account_info"));
                group.setOttId(rs.getString("ott_id_info"));
                group.setOttPw(rs.getString("ott_pw_info"));
                group.setServiceId(rs.getInt("service_id"));
                
                groupList.add(group);
            }
            return groupList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
    
 // 특정 OTT 서비스에 해당하는 OTTGroup 목록 조회
    public List<OTTGroup> getOTTGroupsByConsumerId(long consumerId) {
        String sql = "SELECT o.group_id, period, status, current_members, is_checked, kakao_id_info, account_info, ott_id_info, ott_pw_info, service_id "
              + "FROM OTT_GROUP o, GROUP_MEMBER m WHERE o.group_id = m.group_id AND m.CONSUMER_ID = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] { consumerId });

        try {
           ResultSet rs = jdbcUtil.executeQuery();
           List<OTTGroup> groupList = new ArrayList<>();
            while (rs.next()) {
               OTTGroup group = new OTTGroup();
               
               group.setGroupId(rs.getLong("group_id"));
                group.setPeriod(rs.getDate("period"));
                group.setStatus(rs.getInt("status"));
                group.setCurrentMembers(rs.getInt("current_members"));
                group.setIsChecked(rs.getInt("is_checked"));
                group.setKakaoId(rs.getString("kakao_id_info"));
                group.setAccount(rs.getString("account_info"));
                group.setOttId(rs.getString("ott_id_info"));
                group.setOttPw(rs.getString("ott_pw_info"));
                group.setServiceId(rs.getInt("service_id"));
                
                groupList.add(group);
            }
            return groupList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    // OTTGroup 정보 업데이트
    public int updateOTTGroup(OTTGroup group) {
        String sql = "UPDATE OTT_GROUP SET CURRENT_MEMBERS = ?, STATUS = ?, IS_CHECKED = ?, "
                   + "KAKAO_ID_INFO = ?, ACCOUNT_INFO = ?, OTT_ID_INFO = ?, OTT_PW_INFO = ? WHERE GROUP_ID = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] {
                group.getCurrentMembers(),
                group.getStatus(),
                group.getIsChecked(),
                group.getKakaoId(),
                group.getAccount(),
                group.getOttId(),
                group.getOttPw(),
                group.getGroupId()
            });

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
    

    // SERVICE_ID를 통해 OTTService를 가져오는 메서드
    private OTTService getOTTServiceById(int serviceId) {
        for (OTTService service : OTTService.values()) {
            if (service.getId() == serviceId) {
                return service;
            }
        }
        return null;
    }
    
    // service_id로 OTTService의 세부 정보를 가져오는 메소드
    public OTTService getServiceDetailsByServiceId(int serviceId) {
        String query = "SELECT SERVICE_ID, SERVICE_NAME, IMAGE FROM OTTSERVICE WHERE SERVICE_ID = ?";

        jdbcUtil.setSqlAndParameters(query, new Object[] { serviceId });
        
        try (ResultSet rs = jdbcUtil.executeQuery()) {
            if (rs.next()) {
                for (OTTService service : OTTService.values()) {
                    if (service.getId() == serviceId) {
                        service.setImage(rs.getString("IMAGE"));  // DB에서 이미지 URL 가져오기
                        return service;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
    
    

}
