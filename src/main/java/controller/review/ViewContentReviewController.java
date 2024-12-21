package controller.review;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.domain.Content;
import model.dto.Review;
import model.service.ContentManager;
import model.service.ReviewManager;

public class ViewContentReviewController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String type = request.getParameter("type");
        String contentId = request.getParameter("contentId");
        if (contentId == null) {
            return "redirect:/content/view?type=" + type;
        }

        // 리뷰 목록 조회
        ReviewManager reviewManager = ReviewManager.getInstance();
        ContentManager contentManager = new ContentManager();
        List<Review> reviewList = reviewManager.getReviewsByContentId(contentId);
        Content content = contentManager.getContentById(contentId);
        
        //콘텐츠 리뷰 정보 조회
        Map contentReview = reviewManager.getMeanRatingAndCount(contentId);
        Float mean = ((Double) contentReview.get("MEAN")).floatValue();
        Long count = Long.parseLong(String.valueOf(contentReview.get("COUNT")));
        
        request.setAttribute("content", content);
        request.setAttribute("reviewList", reviewList);
        request.setAttribute("meanRating", mean);
        request.setAttribute("count", count);
        
        return "/review/contentView.jsp"; // 리뷰 페이지로 이동
    }
}
