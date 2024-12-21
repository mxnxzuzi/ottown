package controller.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.consumer.UserSessionUtils;
import model.dto.Review;
import model.service.ReviewManager;

public class AddReviewController implements Controller{
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        HttpSession session = request.getSession();
        
        if (!UserSessionUtils.hasLogined(session)) { return "redirect:/user/login/form"; }
        
        String type = request.getParameter("type");
        if (type == null) {
            return "redirect:/content/view"; 
        }
        String contentId = request.getParameter("contentId");
        if (contentId == null) {
            return "redirect:/content/view?type=" + type; // 기본 페이지로 리디렉션
        }
        String consumerId = UserSessionUtils.getLoginUserId(session);
        String rating = request.getParameter("rating");
        String reviewText = request.getParameter("reviewText");
        

        Review review = new Review();
        review.setConsumerId(Long.parseLong(consumerId));
        review.setContentId(Long.parseLong(contentId));
        if (rating != null && !rating.trim().isEmpty() && reviewText != null && !reviewText.trim().isEmpty()) {
            try {
                float ratingValue = Float.parseFloat(rating);
                if (ratingValue < 0.0 || ratingValue > 5.0) {
                    throw new IllegalArgumentException("평점은 0.0과 5.0 사이여야 합니다.");
                }
                review.setRating(ratingValue);
            } catch (NumberFormatException e) {
                return "redirect:/content/review/view?contentId=" + contentId;
            }
            review.setReviewText(reviewText);

            
            ReviewManager manager = ReviewManager.getInstance();
            manager.addReview(review);
        }
        
        return "redirect:/content/review/view?contentId=" + contentId; // 리뷰 페이지로 이동
    }
}
