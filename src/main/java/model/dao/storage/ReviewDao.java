package model.dao.storage;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.dto.Review;
import model.dao.JDBCUtil;

public class ReviewDao {
private JDBCUtil jdbcUtil = null;   // JDBCUtil 필드 선언
    
    public ReviewDao() {
        jdbcUtil = new JDBCUtil();  
    }
    
    public Review addReview(long consumerId, long contentId, String reviewText, int rating, Date reviewDate) {
        try {
            String query = "INSERT INTO review (consumer_id, content_id, review_text, rating, review_date) VALUES (?, ?, ?, ?, ?)";
            jdbcUtil.setSqlAndParameters(query, new Object[]{consumerId, contentId, reviewText, rating, reviewDate});
            jdbcUtil.executeUpdate();
            
            query = "SELECT review_id, consumer_id, content_id, review_text, rating FROM REVIEW WHERE consumer_id = ? AND content_id = ?";
            jdbcUtil.setSqlAndParameters(query, new Object[]{consumerId, contentId});
            ResultSet rs = jdbcUtil.executeQuery();
            Review review = new Review(rs.getLong("review_id"), consumerId, contentId, reviewText, rating, reviewDate);
            
            return review;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    public List<Review> getReviewsByContentId(int contentId) {
        String query = "SELECT review_id, consumer_id, content_id, review_text, rating, review_date FROM review WHERE content_id = ?";
        
        jdbcUtil.setSqlAndParameters(query, new Object[]{contentId});
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            List<Review> reviewList = new ArrayList<>();
            
            while (rs.next()) {
                Review review = new Review();
                review.setReviewId(rs.getLong("id"));
                review.setConsumerId(rs.getLong("userId"));
                review.setContentId(rs.getLong("contentId"));
                review.setReviewText(rs.getString("reviewText"));
                review.setRating(rs.getInt("rating"));
                review.setReviewDate(rs.getDate("review_date"));
                reviewList.add(review);
            }
            return reviewList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
    
 // 리뷰 수정
    public void updateReview(Review review) {
        String query = "UPDATE Review SET review_text = ?, rating = ? WHERE review_id = ?";
        jdbcUtil.setSqlAndParameters(query, new Object[] {review.getReviewText(), review.getRating(), review.getReviewId()});
        try {
            jdbcUtil.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
    }

    // 리뷰 삭제
    public void deleteReview(Long reviewId) {
        String query = "DELETE FROM Review WHERE review_id = ?";

        jdbcUtil.setSqlAndParameters(query, new Object[] {reviewId});
        try {
            jdbcUtil.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
    }
}
