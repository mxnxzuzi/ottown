package model.domain;

import java.util.Date;
import java.util.List;

public class Content {
    private Long contentId;         // 콘텐츠 ID
    private String title;          // 제목
    private String type;           // 타입 (드라마, TV프로그램)
    private String image;          // 이미지 (URL 또는 Base64 인코딩 문자열)
    private Date publishDate;      // 출시 날짜
    private List<String> ottServices;
    private boolean isLiked = false;
    

    public Content() {
    	
    }
    // 매개변수 생성자
    public Content(Long contentId, String title, String type, String image, Date publishDate) {
        this.contentId = contentId;
        this.title = title;
        this.type = type;
        this.image = image;
        this.publishDate = publishDate;
    }

	// Getter and Setter
    public List<String> getOttServices() {
        return ottServices;
    }

    public void setOttServices(List<String> ottServices) {
        this.ottServices = ottServices;
    }
    
    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
    
    public boolean getIsLiked() {
        return isLiked;
    }
    
    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    @Override
    public String toString() {
        return "Content{" +
                "contentId=" + contentId +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", image='" + image + '\'' +
                ", publishDate=" + publishDate +
                '}';
    }
}