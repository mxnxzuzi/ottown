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
        
        if (!UserSessionUtils.hasLogined(session)) { return "redirect:/user/login/form"; }
        
        String reviewId = request.getParameter("reviewId");
        String rating = request.getParameter("rating");
        String reviewText = request.getParameter("reviewText");
        

        Review review = new Review();
        review.setReviewId(Long.parseLong(reviewId));
        review.setRating(Float.parseFloat(rating));
        review.setReviewText(reviewText);
        
        ReviewManager manager = ReviewManager.getInstance();
        manager.updateReview(review);
        
        return "redirect:/mypage/review/view"; // 리뷰 페이지로 이동
    }

}
