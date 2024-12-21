package model.service;

import model.domain.Consumer;
import model.domain.OTTGroupMember;
import model.domain.OTTGroup;
import model.dao.OTTGroup.OTTGroupDao;
import model.dao.OTTGroupMember.OTTGroupMemberDao;
import java.util.List;

public class OTTGroupMemberManager {

    private OTTGroupMemberDao ottGroupMemberDao;

    // 생성자: DAO 인스턴스화
    public OTTGroupMemberManager() {
        this.ottGroupMemberDao = new OTTGroupMemberDao();  // 멤버 DAO
    }

    // OTTGroup에 멤버 추가
    public boolean addMemberToGroup(int groupId, OTTGroupMember member) throws Exception {
        OTTGroupDao ottGroupDao = new OTTGroupDao();
        OTTGroup group = ottGroupDao.getOTTGroupById(groupId);

        if (group == null || group.getCurrentMembers() >= 4) {
            return false; // 그룹이 없거나 최대 멤버 수 초과
        }

        member.setGroup(group);
        boolean result = ottGroupMemberDao.addOTTGroupMember(member);

        if (result == true) {
            group.setCurrentMembers(group.getCurrentMembers() + 1);
            ottGroupDao.updateOTTGroup(group);
        }
        return result;
    }

    // 그룹에서 특정 멤버를 삭제하는 메서드
    public boolean removeMemberFromGroup(int groupId, int memberId) throws Exception {
        if (ottGroupMemberDao.isMemberInGroup(groupId, memberId)) {
            return ottGroupMemberDao.removeOTTGroupMember(memberId);
        }
        return false;
    }

    // 입금 상태 설정
    public boolean setPaid(int ottGroupMemberId, int ottGroupId) throws Exception {
        return ottGroupMemberDao.updatePaidStatus(ottGroupMemberId, ottGroupId);
    }

    // OTTGroup에 속한 멤버 수 카운트
    public int countMembersInGroup(int groupId) {
        return ottGroupMemberDao.countMembersByOTTGroupId(groupId);
    }

    // OTTGroup에 속한 모든 멤버 조회
    public List<OTTGroupMember> getMembersOfGroup(int groupId) {
        return ottGroupMemberDao.getMembersByOTTGroupId(groupId);
    }

    // 특정 OTTGroup의 모든 멤버가 입금했는지 확인
    public boolean areAllMembersPaid(int groupId) {
        return ottGroupMemberDao.areAllMembersPaid(groupId);
    }

    // 특정 OTTGroup에 대한 호스트의 체크 상태 확인
    public boolean isHostChecked(int groupId) {
        return ottGroupMemberDao.isHostChecked(groupId);
    }

    // memberId를 통해 해당 Consumer 정보 가져오기
    public Consumer getConsumerById(int memberId) throws Exception {
        // OTTGroupMemberDao에서 getConsumerByMemberId 메서드 호출
        return ottGroupMemberDao.getConsumerByMemberId(memberId);
    }

    // Consumer ID를 통해 Consumer 정보 가져오기
    public Consumer getConsumerById(long consumerId) throws Exception {
        // OTTGroupMemberDao에서 getConsumerById 메서드 호출
        return ottGroupMemberDao.getConsumerById(consumerId);
    }
    
 // role이 true인 멤버의 consumerId를 찾는 메서드
    public Long getConsumerIdByRoleTrue(int ottGroupMemberId) throws Exception {
        return ottGroupMemberDao.getConsumerIdByRoleTrue(ottGroupMemberId);  // DAO에서 메서드 호출
    }
    
    public OTTGroupMember findMemberByConsumerId(int consumerId) throws Exception {
        return ottGroupMemberDao.findMemberByConsumerId(consumerId);
    }
}
