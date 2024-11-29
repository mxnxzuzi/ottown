package controller.storage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
//import controller.user.UserSessionUtils;
import model.service.StorageManager;

public class DeleteFavController implements Controller{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        HttpSession session = request.getSession();
      //UserSessionUtils 수정하기
		/*
		 * if (!UserSessionUtils.hasLogined(session)) { return
		 * "redirect:/user/login/form"; // login form 요청으로 redirect(uri 수정하기) }
		 */
        request.setCharacterEncoding("utf-8");
        
        String consumerId = "1";
        //String consumerId = UserSessionUtils.getLoginUserId(session);
        String contentId = request.getParameter("contentId");
        String type = request.getParameter("type");
        
        StorageManager manager = StorageManager.getInstance();
        int count = manager.deleteFav(contentId, consumerId);
        
        if (type == null) {
            return "redirect:/content/view"; 
        }
        switch (type) {
            case "movie":
                return "redirect:/content/view?type=movie";
            case "drama":
                return "redirect:/content/view?type=movie";
            case "animation":
                return "redirect:/content/view?type=movie";
            default:
                return "redirect:/content/view";
        }
    }
    
}
