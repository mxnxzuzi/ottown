package model.domain;

import java.sql.Date;

public class Content {
    private Long contentId;
    private String title;
    private String type;
    private String genre;
    private String image; 
    private Date publishDate;
    
    public Content() {}

    public Content(Long contentId, String title, String type, String genre, String image, Date publishDate) {
        this.contentId = contentId;
        this.title = title;
        this.type = type;
        this.genre = genre;
        this.image = image;
        this.publishDate = publishDate;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    @Override
    public String toString() {
        return "Content{" +
                "contentId=" + contentId +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", genre='" + genre + '\'' +
                ", image='" + image + '\'' +
                ", publishDate=" + publishDate +
                '}';
    }
}

