package model.service;

import model.domain.OTTGroupMember;
import model.domain.OTTService;
//import model.domain.OTTService;
import model.domain.OTTGroup;
//import model.domain.Consumer;
import model.dao.OTTGroup.OTTGroupDao;
import model.dao.OTTGroupMember.OTTGroupMemberDao;
//import java.sql.*;
import java.util.List;

public class OTTGroupManager {
    private OTTGroupDao ottGroupDao;      // OTTGroup 데이터를 다루는 DAO
    private OTTGroupMemberDao ottGroupMemberDao; // OTTGroupMember 데이터를 다루는 DAO

    // 생성자: DAO 인스턴스화
    public OTTGroupManager() {
        this.ottGroupDao = new OTTGroupDao();
        this.ottGroupMemberDao = new OTTGroupMemberDao();
    }

    // 새로운 OTTGroup 생성
    public OTTGroup createOTTGroup(int userId, String kakaoId, String account, String ottId, String ottPw, OTTService ottService) throws Exception {
        // OTTGroup 객체 생성
        OTTGroup newGroup = new OTTGroup();
        newGroup.setCreateDate((int) (System.currentTimeMillis() / 1000));  // 현재 시간을 Unix timestamp로 설정
        newGroup.setStatus(1);  // 모집 중으로 설정
        newGroup.setCurrentMembers(1);  // 현재 1명 (사용자)
        newGroup.setChecked(0);  // 초기 상태에서 체크되지 않음
        newGroup.setAccount(account);
        newGroup.setKakaoId(kakaoId);
        newGroup.setOttId(ottId);
        newGroup.setOttPw(ottPw);
        newGroup.setOttService(ottService);
        
        // 방 생성 DB에 저장
        int groupId = ottGroupDao.createOTTGroup(newGroup);
        if (groupId > 0) {
            newGroup.setId(groupId);  // DB에서 반환된 ID 설정
        }

        // 그룹 생성 후 호스트 추가
        addHostAsMember(newGroup);

        return newGroup;  // 생성된 공동구매방 객체 반환
    }

    // 방에 호스트를 자동으로 추가하는 메서드
    private void addHostAsMember(OTTGroup ottGroup) throws Exception {
        // OTTGroupMember 객체 생성 (호스트 역할 설정)
        OTTGroupMember hostMember = new OTTGroupMember();
        hostMember.setRole(1);  // 호스트 역할 설정
        hostMember.setPaid(1);  // 기본값: 결제 완료
        hostMember.setRemoved(0); // 삭제되지 않음
        hostMember.setGroup(ottGroup); // 그룹 설정
//        hostMember.setConsumer(new Consumer(ottGroup.getKakaoId())); // 카카오 아이디로 사용자 설정

        // 호스트를 OTTGroupMember에 추가
        ottGroupMemberDao.addOTTGroupMember(hostMember);
    }

    public boolean joinGroup(int userId, int groupId) throws Exception {
        // 그룹 조회
        OTTGroup group = ottGroupDao.getOTTGroupById(groupId);
        if (group == null) {
            return false;  // 그룹이 존재하지 않으면 실패
        }
    
        // 그룹의 최대 참여자 수 초과 여부 확인
        if (group.getCurrentMembers() >= 4) {  // 예시로 4명이 최대 참여자 수로 가정
            return false;  // 그룹의 최대 인원 수를 초과할 수 없음
        }
    
        // 참여할 사용자 조회
//        Consumer consumer = consumerDao.getUserById(userId);
//        if (consumer == null) {
//            return false;  // 사용자가 존재하지 않으면 실패
//        }
    
        // OTTGroupMember 생성 (사용자 추가)
        OTTGroupMember newMember = new OTTGroupMember();
//        newMember.setUser(user);
        newMember.setGroup(group);
        newMember.setRole(0);  // 기본적으로 일반 사용자 역할
        newMember.setPaid(0);  // 기본값은 결제되지 않음
    
        // 그룹에 사용자를 추가
        boolean memberAdded = ottGroupMemberDao.addOTTGroupMember(newMember);
        if (memberAdded) {
            // 그룹의 현재 멤버 수 증가
            group.setCurrentMembers(group.getCurrentMembers() + 1);
            ottGroupDao.updateOTTGroup(group);  // 그룹 정보 업데이트
            return true;
        }
        return false;  // 실패 시 false 반환
    }
    
    // 특정 userId와 groupId에 해당하는 OTTGroup 조회
    public OTTGroup findOTTGroup(int groupId) {
        // DAO를 통해 OTTGroup 정보를 조회
        OTTGroup ottGroup = ottGroupDao.getOTTGroupById(groupId);
        
        if (ottGroup != null) {
            // OTTGroup이 존재하면 추가 로직 처리 (예: 호스트 여부 확인 등)
            return ottGroup;
        }
        return null;  // OTTGroup이 없으면 null 반환
    }
    
    // 그룹의 호스트 조회
    public boolean isHostOfRoom(int userId, int roomId) {
    	List<OTTGroupMember> members = ottGroupDao.getOTTGroupMembers(roomId);
    	
    	for (OTTGroupMember member : members) {
            if (member.getId() == userId) {
                if (member.isRole()==1) {
                	return true;
                }
            }
        }
    	return false;
    }
    
    // 멤버 강퇴
    public boolean kickRoomMember(int roomMemberId) throws Exception {
        return ottGroupMemberDao.removeOTTGroupMember(roomMemberId);
    }

    
    // 사용자가 그룹의 멤버인지 확인
    public boolean isMemberOfGroup(int userId, int groupId) {
        return ottGroupMemberDao.isMemberInGroup(userId, groupId);
    }

    // OTT별 Group list 조회
 // OTT별 Group list 조회
    public List<OTTGroup> showOTTGroupList(OTTService ottService) {
        return ottGroupDao.getOTTGroupsByOttService(ottService);
    }


    // OTTGroup 상태 변경 (모집 완료=false / 모집 중=true)
    public boolean updateGroupStatus(int groupId) {
        OTTGroup ottGroup = ottGroupDao.getOTTGroupById(groupId);
        if (ottGroup == null) {
            return false;
        }

        int memberCount = ottGroupMemberDao.countMembersByOTTGroupId(groupId);
        if(memberCount >= 4) {
        	ottGroup.setStatus(1);
        }
        else {
        	ottGroup.setStatus(0);
        }
        

        return true;
    }
    
    // 특정 OTTGroup의 상태를 확인하는 메서드
    public OTTGroup getGroupStatus(int groupId) {
        return ottGroupDao.getOTTGroupById(groupId);
    }

    // 모든 그룹의 멤버 수를 조회하는 메서드
    public int countMembers(int groupId) {
        return ottGroupMemberDao.countMembersByOTTGroupId(groupId);
    }
    
    public OTTService getServiceDetailsByServiceId(String serviceId) {
        return ottGroupDao.getServiceDetailsByServiceId(Integer.parseInt(serviceId));
    }
    
    // 특정 OTT 서비스에 해당하는 OTTGroup 목록을 가져오는 메서드
    public List<OTTGroup> getOTTGroupsByOttService(OTTService ottService) {
        return ottGroupDao.getOTTGroupsByOttService(ottService); // DAO 메서드 호출
    }
    
    // 그룹 계정 공유 활성화 메서드
    public boolean activateOTTSharing(int groupId) throws Exception {
        OTTGroup group = ottGroupDao.getOTTGroupById(groupId);
        if (group != null) {
            group.setChecked(1); // isChecked 값을 1로 설정
            return ottGroupDao.updateOTTGroup(group) > 0; // 업데이트 성공 여부 반환
        }
        return false;
    }

}
