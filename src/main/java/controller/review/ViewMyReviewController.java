package controller.review;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.consumer.UserSessionUtils;
import model.dao.consumer.ConsumerDao;
import model.domain.Review;
import model.service.ConsumerManager;
import model.service.ReviewManager;

public class ViewMyReviewController implements Controller {

    private final ConsumerManager consumerManager = new ConsumerManager();
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        
        if (!UserSessionUtils.hasLogined(session)) { return "redirect:/consumer/login"; }
        
        // 리뷰 목록 조회
        ReviewManager reviewManager = ReviewManager.getInstance();
        String consumerId = UserSessionUtils.getLoginUserId(session);
        
        List<Review> reviewList = reviewManager.getReviewsByConsumerId(consumerId);
        String consumerName = consumerManager.getConsumerById(Long.parseLong(consumerId)).getConsumerName();
        
        request.setAttribute("consumerName", consumerName);
        request.setAttribute("reviewList", reviewList);
        
        return "/review/myView.jsp";
    }

}
