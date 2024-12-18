package controller.homepage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.dao.content.ReviewDao;
import model.dto.Review;

public class ViewContentReviewController implements Controller {
    private final ReviewDao reviewDao = new ReviewDao();
    private final ConsumerDao consumerDao = new ConsumerDao();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contentId = request.getParameter("contentId");
        if (contentId == null) {
            return "redirect:/content/view"; // 기본 페이지로 리디렉션
        }

        // 리뷰 목록 조회
        List<Review> reviewList = reviewDao.getReviewsByContentId(Long.parseLong(contentId));
        String contentName = consumerDao.getConsumerById(Long.parseLong(contentId));
        
        request.setAttribute("reviewList", reviewList);
        request.setAttribute("contentId", contentId);
        request.setAttribute("contentName", contentName);

        return "/review/reviewPage.jsp"; // 리뷰 페이지로 이동
    }
}
