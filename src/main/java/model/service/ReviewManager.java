package model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import model.dao.content.ReviewDao;
import model.dto.Review;

public class ReviewManager {
    private static ReviewManager reviewMan = new ReviewManager();
    private ReviewDao reviewDao;
    
    private ReviewManager() {
        try {
            reviewDao = new ReviewDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static ReviewManager getInstance() {
        return reviewMan;
    }
    
    public int addReview(Review review) throws SQLException{
        return reviewDao.addReview(review);
    }

    // 특정 콘텐츠의 리뷰 조회
    public List<Review> getReviewsByContentId(String contentId) throws SQLException{
        return reviewDao.getReviewsByContentId(Long.parseLong(contentId));
    }
    
    // 특정 유저의 리뷰 조회
    public List<Review> getReviewsByConsumerId(String consumerId) throws SQLException{
        return reviewDao.getReviewsByConsumerId(Long.parseLong(consumerId));
    }
    
    public Map getMeanRatingAndCount(String contentId) throws SQLException{
        return reviewDao.getMeanRatingAndCount(Long.parseLong(contentId));
    }

    // 리뷰 수정
    public int updateReview(Review review) throws SQLException{
        return reviewDao.updateReview(review);
    }

    // 리뷰 삭제
    public int deleteReview(String reviewId) throws SQLException{
        return reviewDao.deleteReview(Long.parseLong(reviewId));
    }
}
