package model.dto;

public class OTTService {
    private int serviceId;         // 서비스 ID
    private String serviceName;    // OTT 서비스 이름
    private String image;          // 이미지 경로 또는 Base64 인코딩된 문자열

    // 기본 생성자
    public OTTService() {
    }

    // 매개변수 생성자
    public OTTService(int serviceId, String serviceName, String image) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.image = image;
    }

    // Getter and Setter
    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "OTTService{" +
                "serviceId=" + serviceId +
                ", serviceName='" + serviceName + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}