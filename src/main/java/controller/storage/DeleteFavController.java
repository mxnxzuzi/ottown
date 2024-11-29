package controller.storage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.user.UserSessionUtils;
import model.service.StorageManager;

public class DeleteFavController implements Controller{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        HttpSession session = request.getSession();
      //UserSessionUtils 수정하기
        if (!UserSessionUtils.hasLogined(session)) {
            return "redirect:/user/login/form";     // login form 요청으로 redirect
        }
        request.setCharacterEncoding("utf-8");
        
        String consumerId = UserSessionUtils.getLoginUserId(session);
        String contentId = request.getParameter("contentId");
        
        StorageManager manager = StorageManager.getInstance();
        int count = manager.deleteFav(contentId, consumerId);
        
        return null;
    }
    
}
