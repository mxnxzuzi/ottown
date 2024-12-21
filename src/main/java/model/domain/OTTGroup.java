package model.domain;

//import model.domain.Consumer;
//import model.domain.OTTGroupMember;
import java.util.ArrayList;
import java.util.List;

public class OTTGroup {
    private int id;
    private int createDate; 
    private int status; // 상태: 모집 완료(0) / 모집 중(1)
    private int currentMembers;
    private int isChecked;	// 공유: 공유 전(0) / 공유 후(1)
    private String account;
    private String kakaoId;
    private String ottId;
    private String ottPw;
    private OTTService ottService;
    private List<OTTGroupMember> members;

    // 기본 생성자
    public OTTGroup() {}

    // 매개변수 생성자
    
    public OTTGroup(int id) {
        this.id = id;
    }
    
    public OTTGroup(int id, int createDate, int status, int currentMembers, int isChecked, String kakaoId, String ottId, String ottPw) {
        this.id = id;
        this.createDate = createDate;
        this.status = status;
        this.currentMembers = currentMembers;
        this.isChecked = isChecked;
        this.kakaoId = kakaoId;
        this.ottId = ottId;
        this.ottPw = ottPw;
        this.members = new ArrayList<>();;
    }

    // Getter 및 Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreateDate() {
        return createDate;
    }

    public void setCreateDate(int createDate) {
        this.createDate = createDate;
    }

    public int isStatus() {
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

    public int isChecked() {
        return isChecked;
    }

    public void setChecked(int isChecked) {
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
    
    public List<OTTGroupMember> getMembers() {
        return members;
    }

    public void setMembers(List<OTTGroupMember> members) {
        this.members = members;
    }

    public OTTService getOttService() {
        return ottService;
    }

    public void setOttService(OTTService ottService) {
        this.ottService = ottService;
    }

    public List<String> showMembers() {
        List<String> memberInfo = new ArrayList<>();
        
        for(OTTGroupMember member : members) {
            memberInfo.add("OTTGroupMember [memberId=" + member.getId() + ", isPaid=" + member.isPaid() + "]");
        }
        
        return memberInfo;
    }
}
