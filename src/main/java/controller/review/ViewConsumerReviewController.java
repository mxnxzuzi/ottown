package controller.review;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.consumer.UserSessionUtils;
import model.dao.consumer.ConsumerDao;
import model.dto.Review;
import model.service.ReviewManager;

public class ViewConsumerReviewController implements Controller {
  //Manager로 변경하기
    private final ConsumerDao consumerDao = new ConsumerDao();
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        
        if (!UserSessionUtils.hasLogined(session)) { return "redirect:/consumer/login"; }
        
        // 리뷰 목록 조회
        ReviewManager reviewManager = ReviewManager.getInstance();
        String consumerId = request.getParameter("consumerId");
        List<Review> reviewList = reviewManager.getReviewsByConsumerId(consumerId);
        
        request.setAttribute("reviewList", reviewList);
        String consumerName = consumerDao.getConsumerById(Long.parseLong(consumerId)).getConsumerName();
        
        request.setAttribute("consumerName", consumerName);
        
        String sessionId = UserSessionUtils.getLoginUserId(session);
        if (consumerId.equals(sessionId)) {
            return "redirect:/mypage/review/view";
        }
        else {
            return "/review/consumerView.jsp"; // 리뷰 페이지로 이동
        }
    }
}
