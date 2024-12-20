package controller.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.consumer.UserSessionUtils;
import model.service.ReviewManager;

public class DeleteReviewController implements Controller{
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        HttpSession session = request.getSession();
        
        if (!UserSessionUtils.hasLogined(session)) { return "redirect:/user/login/form"; }
        
        String reviewId = request.getParameter("reviewId");
        
        ReviewManager manager = ReviewManager.getInstance();
        manager.deleteReview(reviewId);
        
        return "redirect:/mypage/review/view"; // 리뷰 페이지로 이동
    }

}
