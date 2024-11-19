package model.dto;

import java.sql.Date;

public class Review {
    private Long reviewId;
    private Long contentId;
    private Long consumerId;
    private String reviewText;
    private int rating;
    private Date reviewDate;
    
    public Review() {}
    
    public Review(Long reviewId, Long contentId, Long consumerId, String reviewText, int rating, Date reviewDate) {
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }
    
}
