package model.domain;

import java.sql.Date;

public class Consumer {
    private Long consumerId;
    private String consumerName;
    private int loginType;
    private Date updateDate;

    public Consumer() {}

    public Consumer(Long consumerId, String consumerName, int loginType, Date updateDate) {
        this.consumerId = consumerId;
        this.consumerName = consumerName;
        this.loginType = loginType;
        this.updateDate = updateDate;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    // consumerName getter와 setter
    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}

