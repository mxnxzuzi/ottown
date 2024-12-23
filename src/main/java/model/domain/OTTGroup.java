package model.domain;

import java.sql.Date;

public class OTTGroup {
    private long groupId; // 수정: id -> groupId, 타입 변경: int -> long
    private Date period;
    private int status;
    private int currentMembers;
    private int isChecked;
    private String account;
    private String kakaoId;
    private String ottId;
    private String ottPw;
    private int serviceId;

    // 기본 생성자
    public OTTGroup() {}

   public OTTGroup(long groupId, Date period, int status, int currentMembers, int isChecked, String account,
         String kakaoId, String ottId, String ottPw, int serviceId) {
      super();
      this.groupId = groupId;
      this.period = period;
      this.status = status;
      this.currentMembers = currentMembers;
      this.isChecked = isChecked;
      this.account = account;
      this.kakaoId = kakaoId;
      this.ottId = ottId;
      this.ottPw = ottPw;
      this.serviceId = serviceId;
   }

   public long getGroupId() {
      return groupId;
   }

   public void setGroupId(long groupId) {
      this.groupId = groupId;
   }

   public Date getPeriod() {
      return period;
   }

   public void setPeriod(Date period) {
      this.period = period;
   }

   public int getStatus() {
      return status;
   }

   public void setStatus(int status) {
      this.status = status;
   }

   public int getCurrentMembers() {
      return currentMembers;
   }

   public void setCurrentMembers(int currentMembers) {
      this.currentMembers = currentMembers;
   }

   public int getIsChecked() {
      return isChecked;
   }

   public void setIsChecked(int isChecked) {
      this.isChecked = isChecked;
   }

   public String getAccount() {
      return account;
   }

   public void setAccount(String account) {
      this.account = account;
   }

   public String getKakaoId() {
      return kakaoId;
   }

   public void setKakaoId(String kakaoId) {
      this.kakaoId = kakaoId;
   }

   public String getOttId() {
      return ottId;
   }

   public void setOttId(String ottId) {
      this.ottId = ottId;
   }

   public String getOttPw() {
      return ottPw;
   }

   public void setOttPw(String ottPw) {
      this.ottPw = ottPw;
   }

   public int getServiceId() {
      return serviceId;
   }

   public void setServiceId(int serviceId) {
      this.serviceId = serviceId;
   }
}
