package controller.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.consumer.UserSessionUtils;
import model.dto.Review;
import model.service.ReviewManager;

public class UpdateReviewController implements Controller{
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        HttpSession session = request.getSession();
        
        if (!UserSessionUtils.hasLogined(session)) { return "redirect:/consumer/login"; }
        
        String reviewId = request.getParameter("reviewId");
        String rating = request.getParameter("rating");
        String reviewText = request.getParameter("reviewText");
        

        Review review = new Review();
        
        // reviewId 유효성 확인
        if (reviewId != null || !reviewId.trim().isEmpty()) {
            review.setReviewId(Long.parseLong(reviewId));
        }    
        
        if (rating != null && !rating.trim().isEmpty() && reviewText != null && !reviewText.trim().isEmpty()) {
            try {
                float ratingValue = Float.parseFloat(rating);
                if (ratingValue < 0.0 || ratingValue > 5.0) {
                    throw new IllegalArgumentException("평점은 0.0과 5.0 사이여야 합니다.");
                }
                review.setRating(Float.parseFloat(rating));
            } catch (NumberFormatException e) {
                return "redirect:/mypage/review/view";
            }
            review.setReviewText(reviewText);

            ReviewManager manager = ReviewManager.getInstance();
            manager.updateReview(review);
        }
        
        return "redirect:/mypage/review/view"; // 리뷰 페이지로 이동
    }

}
