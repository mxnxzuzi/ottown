package model.domain;

import java.sql.Timestamp;

public class Consumer {
    private Long consumerId;
    private String email;
    private String password;
    private String consumerName;
    private int loginType;
    private Timestamp updateDate;
    private Timestamp joinDate;

    // 기본 생성자
    public Consumer() {
        // 생성 시점에 현재 시간을 초기화
        this.updateDate = new Timestamp(System.currentTimeMillis());
        this.joinDate = new Timestamp(System.currentTimeMillis());
    }

    // 매개변수 생성자
    public Consumer(Long consumerId, String email, String password) {
        this.consumerId = consumerId;
        this.email = email;
        this.password = password;
        // 생성 시점에 현재 시간을 초기화
        this.updateDate = new Timestamp(System.currentTimeMillis());
        this.joinDate = new Timestamp(System.currentTimeMillis());
    }

    // Getter and Setter for consumerId
    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    // Getter and Setter for consumerName
    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    // Getter and Setter for loginType
    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    // Getter and Setter for updateDate
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public Timestamp getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
