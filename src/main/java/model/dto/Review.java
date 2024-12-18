package model.dto;

import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class Review implements Serializable{
    private Long reviewId;
    private Long contentId;
    private Long consumerId;
    private String reviewText;
    private float rating;
    private Date reviewDate;
    
    public Review() {}
    
    public Review(Long reviewId, Long contentId, Long consumerId, String reviewText, float rating, Date reviewDate) {
        super();
        this.reviewId = reviewId;
        this.contentId = contentId;
        this.consumerId = consumerId;
        this.reviewText = reviewText;
        this.rating = rating;
        this.reviewDate = reviewDate;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }
    
}
