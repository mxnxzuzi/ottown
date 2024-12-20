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
        review.setRating(Float.parseFloat(rating));
        review.setReviewText(reviewText);
        
        ReviewManager manager = ReviewManager.getInstance();
        manager.addReview(review);
        
        return "redirect:/content/review/view?contentId=" + contentId; // 리뷰 페이지로 이동
    }
}
