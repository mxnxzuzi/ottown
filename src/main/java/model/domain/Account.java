package model.domain;

public class Account {
    private String loginId;        // LOGIN_ID
    private Long consumerId;       // CONSUMER_ID
    private String loginPassword;  // LOGIN_PASSWORD
    private String consumerEmail;  // CONSUMER_EMAIL

    // 기본 생성자
    public Account() {}

    // 매개변수 생성자
    public Account(String loginId, Long consumerId, String loginPassword, String consumerEmail) {
        this.loginId = loginId;
        this.consumerId = consumerId;
        this.loginPassword = loginPassword;
        this.consumerEmail = consumerEmail;
    }

    // Getter and Setter for loginId
    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    // Getter and Setter for consumerId
    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    // Getter and Setter for loginPassword
    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    // Getter and Setter for consumerEmail
    public String getConsumerEmail() {
        return consumerEmail;
    }

    public void setConsumerEmail(String consumerEmail) {
        this.consumerEmail = consumerEmail;
    }

    @Override
    public String toString() {
        return "Account{" +
                "loginId='" + loginId + '\'' +
                ", consumerId=" + consumerId +
                ", loginPassword='" + loginPassword + '\'' +
                ", consumerEmail='" + consumerEmail + '\'' +
                '}';
    }
}
