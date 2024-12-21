package model.domain;

//import model.domain.OTTGroup;
//import model.domain.Consumer;

public class OTTGroupMember {
    private int id;
    private int isPaid;	//입금: 입금 전(0) / 입금 후(1)
    private int isRemoved;	//강퇴: 강퇴X(0) / 강퇴O(1)
    private int role;   // true=호스트 / false=멤버
    private Consumer consumer;
    private OTTGroup group;

    // 기본 생성자
    public OTTGroupMember() {}

    // 매개변수 생성자
    public OTTGroupMember(int id, int isPaid, int isRemoved, int role) {
        this.id = id;
        this.isPaid = isPaid;
        this.isRemoved = isRemoved;
        this.role = role;
        this.consumer = new Consumer();
        this.group = new OTTGroup();
    }

    // Getter 및 Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int isPaid() {
        return isPaid;
    }

    public void setPaid(int isPaid) {
        this.isPaid = isPaid;
    }

    public int isRemoved() {
        return isRemoved;
    }

    public void setRemoved(int isRemoved) {
        this.isRemoved = isRemoved;
    }

    public int isRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void addGroupMember(Consumer consumer) {
        this.consumer = consumer;
    }

    public OTTGroup getGroup() {
        return group;
    }

    public void setGroup(OTTGroup group) {
        this.group = group;
    }
    
}
