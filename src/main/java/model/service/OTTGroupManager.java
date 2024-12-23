package model.service;

import model.domain.OTTGroupMember;
import model.domain.OTTService;
import model.domain.Consumer;
import model.domain.OTTGroup;
import model.dao.OTTGroup.OTTGroupDao;
import model.dao.OTTGroupMember.OTTGroupMemberDao;
import java.sql.Date;
import java.util.List;

public class OTTGroupManager {
    private OTTGroupDao ottGroupDao;
    private OTTGroupMemberDao ottGroupMemberDao;

    // 생성자: DAO 인스턴스화
    public OTTGroupManager() {
        this.ottGroupDao = new OTTGroupDao();
        this.ottGroupMemberDao = new OTTGroupMemberDao();
    }

    // 새로운 OTTGroup 생성
    public OTTGroup createOTTGroup(String consumerId, String kakaoId, String account, String ottId, String ottPw, int serviceId) {
        OTTGroup newGroup = new OTTGroup();
        
        newGroup.setAccount(account);
        newGroup.setKakaoId(kakaoId);
        newGroup.setOttId(ottId);
        newGroup.setOttPw(ottPw);
        newGroup.setServiceId(serviceId);

        long groupId = ottGroupDao.createOTTGroup(newGroup);
        if (groupId > 0) {
           newGroup.setGroupId(groupId);
           ottGroupMemberDao.addOTTGroupMember(groupId, Long.parseLong(consumerId));
           ottGroupMemberDao.updateRole(groupId, Long.parseLong(consumerId));
        }

        return newGroup;
    }
    
    public OTTGroup getOTTGroupById(String groudId) {
        return ottGroupDao.getOTTGroupById(Long.parseLong(groudId));
    }

    public boolean joinGroup(long consumerId, long groupId) throws Exception {
        OTTGroup group = ottGroupDao.getOTTGroupById(groupId);
        if (group == null || group.getCurrentMembers() >= 4) {
            return false; // 그룹이 존재하지 않거나 최대 멤버 수 초과
        }

        int memberAdded = ottGroupMemberDao.addOTTGroupMember(groupId, consumerId);
        if (memberAdded > 0) {
           int currentCount = group.getCurrentMembers();
            group.setCurrentMembers(currentCount + 1);
            ottGroupDao.updateOTTGroup(group);
            return true;
        }
        return false;
    }

    
    public List<OTTGroup> getOTTGroupsByConsumerId(String consumerId){
        return ottGroupDao.getOTTGroupsByConsumerId(Long.parseLong(consumerId));
    }

    public boolean kickRoomMember(long groupId, long consumerId) throws Exception {
        return ottGroupMemberDao.removeOTTGroupMember(groupId, consumerId) > 0;
    }

    public boolean isMemberOfGroup(long groupId, long consumerId) {
        if(ottGroupMemberDao.getMemberById(groupId, consumerId) != null) {
           return true;
        }
        
        return false;
     }
    

    public List<OTTGroup> showOTTGroupList(int service_id) {
        return ottGroupDao.getOTTGroupsByServiceId(service_id);
    }

    public boolean activateOTTSharing(long groupId) throws Exception {
        OTTGroup group = ottGroupDao.getOTTGroupById(groupId);
        if (group != null) {
            group.setIsChecked(1);
            return ottGroupDao.updateOTTGroup(group) > 0;
        }
        return false;
    }

    public OTTService getServiceDetailsByServiceId(String serviceId) {
        return ottGroupDao.getServiceDetailsByServiceId(Integer.parseInt(serviceId));
    }
}