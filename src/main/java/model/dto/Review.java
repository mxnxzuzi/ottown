package model.dto;

import java.sql.Date;

import model.domain.Consumer;
import model.domain.Content;

public class Review {
    private Long reviewId;
    private Long contentId;
    private Long consumerId;
    private Content content;
    private Consumer consumer;
    private String reviewText;
    private float rating;
    private Date reviewDate;
    
    public Review() {}
    
    public Review(Long reviewId, Long contentId, Long consumerId, Content content, Consumer consumer, String reviewText, float rating, Date reviewDate) {
        this.reviewId = reviewId;
        this.contentId = contentId;
        this.consumerId = consumerId;
        this.content = content;
        this.consumer = consumer;
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
    
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
    
    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
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
