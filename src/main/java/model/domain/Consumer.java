package model.domain;

import java.sql.Date;

public class Consumer {
    private Long consumerId;
    private String email;
    private String password;
    private String consumerName;
    private int loginType;
    private Date updateDate;
    private Date joinDate;

    // 기본 생성자
    public Consumer() {
        // 생성 시점에 현재 시간을 초기화
        this.updateDate = new Date(System.currentTimeMillis());
        this.joinDate = new Date(System.currentTimeMillis());
    }

    // 매개변수 생성자
    public Consumer(Long consumerId, String email, String password) {
        this.consumerId = consumerId;
        this.email = email;
        this.password = password;
        // 생성 시점에 현재 시간을 초기화
        this.updateDate = new Date(System.currentTimeMillis());
        this.joinDate = new Date(System.currentTimeMillis());
    }

    public Long getConsumerId() {
        return consumerId;
    }
    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public String getConsumerName() { return consumerName; }
    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public int getLoginType() {
        return loginType;
    }
    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public Date getUpdateDate() { return updateDate; }
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getJoinDate() {
        return joinDate;
    }
    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email;
    }
}