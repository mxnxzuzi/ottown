package controller.homepage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.consumer.UserSessionUtils;
import model.domain.Content;
import model.service.ContentManager;

public class ViewContentController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. 요청 파라미터에서 콘텐츠 타입(type) 가져오기
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            // type이 없으면 기본적으로 영화로 설정 (필요에 따라 변경 가능)
            type = "movie";
        }

        HttpSession session = request.getSession();
        String consumerId;
      //UserSessionUtils 수정하기
		if (!UserSessionUtils.hasLogined(session)) { consumerId = null; } 
		consumerId = UserSessionUtils.getLoginUserId(session);
        
        
        // 2. ContentManager를 사용하여 콘텐츠 리스트 가져오기
        ContentManager contentManager = new ContentManager();
        List<Content> contents = contentManager.showContentList(type, consumerId);

        // 3. 요청 속성에 콘텐츠 리스트 저장
        request.setAttribute("contents", contents);
        request.setAttribute("type", type);

        // 4. type에 따라 적절한 JSP 페이지로 반환
		switch (type) {
		case "movie":
		case "drama":
		case "animation":
			return "/homePage/content.jsp?type=" + type; 
		default:
			return "/homePage/content.jsp?type=movie"; 
		}
    }

}