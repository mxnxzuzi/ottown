package model.domain;

public class OTTGroupMember {
    private int isPaid;   // 입금: 입금 전(0) / 입금 후(1)
    private int isRemoved; // 강퇴: 강퇴X(0) / 강퇴O(1)
    private int role; // 1=호스트 / 0=멤버
    private long consumerId;
    private long groupId;

    // 기본 생성자
    public OTTGroupMember() {}

   public OTTGroupMember(int isPaid, int isRemoved, int role, long consumerId, long groupId) {
      super();
      this.isPaid = isPaid;
      this.isRemoved = isRemoved;
      this.role = role;
      this.consumerId = consumerId;
      this.groupId = groupId;
   }

   public int getIsPaid() {
      return isPaid;
   }

   public void setIsPaid(int isPaid) {
      this.isPaid = isPaid;
   }

   public int getIsRemoved() {
      return isRemoved;
   }

   public void setIsRemoved(int isRemoved) {
      this.isRemoved = isRemoved;
   }

   public int getRole() {
      return role;
   }

   public void setRole(int role) {
      this.role = role;
   }

   public long getConsumerId() {
      return consumerId;
   }

   public void setConsumerId(long consumerId) {
      this.consumerId = consumerId;
   }

   public long getGroupId() {
      return groupId;
   }

   public void setGroupId(long groupId) {
      this.groupId = groupId;
   }
}