package model.service;

import java.sql.SQLException;
import java.util.List;

import model.dao.OTTGroup.OTTGroupDao;
import model.dao.OTTGroupMember.OTTGroupMemberDao;
import model.dao.consumer.ConsumerDao;
import model.domain.Consumer;
import model.domain.OTTGroup;
import model.domain.OTTGroupMember;

public class OTTGroupMemberManager {
   private OTTGroupDao ottGroupDao;
   private OTTGroupMemberDao ottGroupMemberDao;
   private ConsumerDao consumerDao;
   
   // 생성자: DAO 인스턴스화
    public OTTGroupMemberManager() {
        this.ottGroupDao = new OTTGroupDao();
        this.ottGroupMemberDao = new OTTGroupMemberDao();
        this.consumerDao = new ConsumerDao();
    }
    
    // 새로운 OTTGroupMember 추가
    public int addOTTGroupMember(long groupId, long consumerId) {
       return ottGroupMemberDao.addOTTGroupMember(groupId, consumerId); 
    }
    
    // member 조회
    public OTTGroupMember getMemberById(long groupId, long consumerId) {
       return ottGroupMemberDao.getMemberById(groupId, consumerId);
    }
    
    // memberList 조회
    public List<OTTGroupMember> getMembersByGroupId(long groupId) {
       return ottGroupMemberDao.getMembersByGroupId(groupId);
    }
    
    // host 조회
    public OTTGroupMember getHost(long groupId) {
       return ottGroupMemberDao.getHost(groupId);
    }

    public String getHostName(long groupId) {
       OTTGroupMember host = ottGroupMemberDao.getHost(groupId);

       try {
            Consumer hostConsumer = consumerDao.getConsumerById(host.getConsumerId());
            System.out.println("hostConsumer " + hostConsumer.toString());
            return hostConsumer.getConsumerName();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       return null;
    }
    
    public int removeOTTGroupMember(long groupId, long consumerId) {
       return ottGroupMemberDao.removeOTTGroupMember(groupId, consumerId);
    }
    
    // 모든 멤버의 입금 상태 확인
    public boolean areAllMembersPaid(long groupId) {
        return ottGroupMemberDao.areAllMembersPaid(groupId);
    }
    
    // 호스트로 업데이트
    public int updateRole(long groupId, long consumerId) {
       return ottGroupMemberDao.updateRole(groupId, consumerId);
    }
    
    // 입금 상태 설정
    public boolean setPaid(long groupId, long consumerId) throws Exception {
        return ottGroupMemberDao.updateIsPaid(groupId, consumerId) > 0;
    }
}
