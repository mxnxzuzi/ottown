package model.dao.OTTGroup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.domain.OTTGroup;
import model.domain.OTTGroupMember;
import model.domain.OTTService;
import model.dao.JDBCUtil;

public class OTTGroupDao {
    private JDBCUtil jdbcUtil = new JDBCUtil();

    public OTTGroupDao() {
        // 기본 생성자
    }

    // OTTGroup 생성
    public int createOTTGroup(OTTGroup ottGroup) throws Exception {
        String sql = "INSERT INTO OTTGroup (createDate, status, currentMembers, isChecked, "
                   + "account, kakaoId, ottId, ottPw, ottService) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcUtil.setSqlAndParameters(sql, new Object[] {
            ottGroup.getCreateDate(),
            ottGroup.isStatus(),
            ottGroup.getCurrentMembers(),
            ottGroup.isChecked(),
            ottGroup.getAccount(),
            ottGroup.getKakaoId(),
            ottGroup.getOttId(),
            ottGroup.getOttPw(),
            ottGroup.getOttService().name() // Enum의 이름을 저장
        });

        try {
            return jdbcUtil.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            jdbcUtil.close();
        }
    }

    // OTTGroup ID로 특정 OTTGroup 조회
    public OTTGroup getOTTGroupById(int ottGroupId) {
        String sql = "SELECT * FROM OTTGroup WHERE id = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] { ottGroupId });

        try (ResultSet rs = jdbcUtil.executeQuery()) {
            if (rs.next()) {
                return mapResultSetToOTTGroup(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    // 특정 OTT 서비스에 해당하는 OTTGroup 목록 조회
    public List<OTTGroup> getOTTGroupsByOttService(OTTService ottService) {
        String sql = "SELECT * FROM OTTGroup WHERE ottService = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] { ottService.name() });

        List<OTTGroup> groupList = new ArrayList<>();
        try (ResultSet rs = jdbcUtil.executeQuery()) {
            while (rs.next()) {
                groupList.add(mapResultSetToOTTGroup(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return groupList;
    }

    // OTTGroup 정보 업데이트
    public int updateOTTGroup(OTTGroup group) throws Exception {
        String sql = "UPDATE OTTGroup SET currentMembers = ?, status = ?, isChecked = ? WHERE id = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] {
            group.getCurrentMembers(),
            group.isStatus(),
            group.isChecked(),
            group.getId()
        });

        try {
            return jdbcUtil.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            jdbcUtil.close();
        }
    }
    
    // service_id로 OTTService의 세부 정보를 가져오는 메소드
    public OTTService getServiceDetailsByServiceId(int serviceId) {
        String query = "SELECT image FROM OTTSERVICE WHERE service_id = ?";

        jdbcUtil.setSqlAndParameters(query, new Object[] {serviceId});
        
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            for (OTTService service : OTTService.values()) {
                if (service.getId() == serviceId) {
                    if (rs.next()) {
                        service.setImage(rs.getString("image"));
                    }
                    return service;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
    
    // 모든 OTT 서비스 목록 조회 메소드
    public List<OTTService> getAllOTTServices() {
        String query = "SELECT service_id, name, image FROM OTTSERVICE";

        jdbcUtil.setSqlAndParameters(query, null); // 파라미터 없음

        List<OTTService> services = new ArrayList<>();

        try {
            ResultSet rs = jdbcUtil.executeQuery();

            // OTTService Enum을 기반으로 설정
            while (rs.next()) {
                for (OTTService service : OTTService.values()) {
                    if (service.getId() == rs.getInt("service_id")) {
                        service.setImage(rs.getString("image"));
                        services.add(service);
                        break; // 일치하는 서비스만 추가
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }

        return services;
    }


    // OTTGroupMember 목록 조회
    public List<OTTGroupMember> getOTTGroupMembers(int ottGroupId) {
        String sql = "SELECT * FROM OTTGroupMember WHERE ottGroupId = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] { ottGroupId });

        List<OTTGroupMember> members = new ArrayList<>();
        try (ResultSet rs = jdbcUtil.executeQuery()) {
            while (rs.next()) {
                members.add(mapResultSetToOTTGroupMember(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return members;
    }

    // ResultSet을 OTTGroup 객체로 변환
    private OTTGroup mapResultSetToOTTGroup(ResultSet rs) throws SQLException {
        OTTGroup ottGroup = new OTTGroup();
        ottGroup.setId(rs.getInt("id"));
        ottGroup.setCreateDate(rs.getInt("createDate"));
        ottGroup.setStatus(rs.getInt("status"));
        ottGroup.setCurrentMembers(rs.getInt("currentMembers"));
        ottGroup.setChecked(rs.getInt("isChecked"));
        ottGroup.setAccount(rs.getString("account"));
        ottGroup.setKakaoId(rs.getString("kakaoId"));
        ottGroup.setOttId(rs.getString("ottId"));
        ottGroup.setOttPw(rs.getString("ottPw"));

        String ottServiceName = rs.getString("ottService");
        ottGroup.setOttService(OTTService.valueOf(ottServiceName));

        return ottGroup;
    }

    // ResultSet을 OTTGroupMember 객체로 변환
    private OTTGroupMember mapResultSetToOTTGroupMember(ResultSet rs) throws SQLException {
        OTTGroupMember member = new OTTGroupMember();
        member.setId(rs.getInt("id"));
        member.setPaid(rs.getInt("isPaid"));
        member.setRemoved(rs.getInt("isRemoved"));
        member.setRole(rs.getInt("role"));
        return member;
    }
    
    // OTT 계정 공유 활성화 메서드
    public void setSharingActive(int groupId) throws Exception {
        String sql = "UPDATE OTTGroup SET isChecked = 1 WHERE id = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[]{groupId});

        try {
            jdbcUtil.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            jdbcUtil.close();
        }
    }
}
